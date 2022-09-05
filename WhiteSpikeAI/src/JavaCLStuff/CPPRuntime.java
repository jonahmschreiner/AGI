package JavaCLStuff;

import static JavaCLStuff.BridJ.debug;
import static JavaCLStuff.BridJ.error;
import static JavaCLStuff.BridJ.info;
import static JavaCLStuff.BridJ.warning;
import static JavaCLStuff.Pointer.allocatePointers;
import static JavaCLStuff.Pointer.getPointer;
import static JavaCLStuff.Pointer.pointerToAddress;
import static JavaCLStuff.Demangler.getClassName;
import static JavaCLStuff.Demangler.getMethodName;
import static JavaCLStuff.DyncallLibrary.DC_CALL_C_DEFAULT;
import static JavaCLStuff.DyncallLibrary.DC_CALL_C_X86_WIN32_THIS_MS;
import static JavaCLStuff.Utils.takeLeft;

import java.io.FileNotFoundException;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import JavaCLStuff.BridJ;
import JavaCLStuff.CRuntime;
import JavaCLStuff.Callback;
import JavaCLStuff.DynamicFunction;
import JavaCLStuff.DynamicFunctionFactory;
import JavaCLStuff.MethodCallInfo;
import JavaCLStuff.NativeEntities.Builder;
import JavaCLStuff.NativeLibrary;
import JavaCLStuff.NativeLibrary.SymbolAccepter;
import JavaCLStuff.NativeObject;
import JavaCLStuff.Platform;
import JavaCLStuff.Pointer;
import JavaCLStuff.PointerIO;
import JavaCLStuff.SizeT;
import JavaCLStuff.Convention;
import JavaCLStuff.Convention.Style;
import JavaCLStuff.Template;
import JavaCLStuff.Virtual;
import JavaCLStuff.Demangler;
import JavaCLStuff.Demangler.IdentLike;
import JavaCLStuff.Demangler.MemberRef;
import JavaCLStuff.Demangler.SpecialName;
import JavaCLStuff.Demangler.Symbol;
import JavaCLStuff.Pair;
import JavaCLStuff.Utils;

public class CPPRuntime extends CRuntime {

    public static final int DEFAULT_CONSTRUCTOR = -1, SKIP_CONSTRUCTOR = -2;

    public static CPPRuntime getInstance() {
        return BridJ.getRuntimeByRuntimeClass(CPPRuntime.class);
    }

    @Override
    public Type getType(NativeObject instance) {
        if (!(instance instanceof CPPObject)) {
            return super.getType(instance);
        }

        Class<?> cls = instance.getClass();
        return new CPPType(cls, getTemplateParameters((CPPObject) instance, cls));
    }

    public static Object[] getTemplateParameters(CPPObject object, Class<?> typeClass) {
        synchronized (object) {
            Object[] params = null;
            if (object.templateParameters != null) {
                params = object.templateParameters.get(typeClass);
            }
            return params;// == null ? new Object[0] : params;
        }
    }

    public static Type[] getTemplateTypeParameters(CPPObject object, Type type) {
        if (!(type instanceof ParameterizedType)) {
            return new Type[0];
        }
        Class<?> typeClass = Utils.getClass(type);
        ParameterizedType pt = (ParameterizedType) type;
        Object[] params = getTemplateParameters(object, typeClass);
        Template t = typeClass.getAnnotation(Template.class);
        if (t == null) {
            throw new RuntimeException("No " + Template.class.getName() + " annotation on class " + typeClass.getName());
        }
        if (t.paramNames().length != params.length) {
            throw new RuntimeException(Template.class.getName() + " annotation's paramNames on class " + typeClass.getName() + " (" + Arrays.asList(t.paramNames()) + " does not match count of actual template params " + Arrays.asList(params));
        }
        if (t.paramNames().length != t.value().length) {
            throw new RuntimeException(Template.class.getName() + " annotation's paramNames and value lengths on class " + typeClass.getName() + " don't match");
        }
        int typeParamCount = pt.getActualTypeArguments().length;
        Type[] ret = new Type[typeParamCount];
        int typeParamIndex = 0;
        for (int i = 0, n = params.length; i < typeParamCount; i++) {
            Object value = t.value()[i];
            if (Type.class.isInstance(value)) {
                ret[typeParamIndex++] = (Type) value;
            }
        }
        assert typeParamIndex == typeParamCount;
        return ret; // TODO cache some of this method
    }

    public void setTemplateParameters(CPPObject object, Class<?> typeClass, Object[] params) {
        synchronized (object) {
            if (object.templateParameters == null) {
                object.templateParameters = (Map) Collections.singletonMap(typeClass, params);
            } else {
                try {
                    // Singleton map might not be mutable.
                    object.templateParameters.put(typeClass, params);
                } catch (Throwable th) {
                    object.templateParameters = new HashMap<Class<?>, Object[]>(object.templateParameters);
                    object.templateParameters.put(typeClass, params);
                }
            }
        }
    }

    protected interface ClassTypeVariableExtractor {

        Type extract(CPPObject instance);
    }

    protected interface MethodTypeVariableExtractor {

        Type extract(CPPObject instance, Object[] methodTemplateParameters);
    }

    protected static int getAnnotatedTemplateTypeVariableIndexInArguments(TypeVariable<?> var) {
        GenericDeclaration d = var.getGenericDeclaration();
        AnnotatedElement e = (AnnotatedElement) d;

        Template t = e.getAnnotation(Template.class);
        if (t == null) {
            throw new RuntimeException(e + " is not a C++ class template (misses the @" + Template.class.getName() + " annotation)");
        }

        int iTypeVar = Arrays.asList(d.getTypeParameters()).indexOf(var);
        int nTypes = 0, iParam = -1;
        Class<?>[] values = t.value();
        for (int i = 0, n = values.length; i < n; i++) {
            Class<?> c = values[i];
            if (c == Class.class || c == Type.class) {
                nTypes++;
            }

            if (nTypes == iTypeVar) {
                iParam = i;
                break;
            }
        }
        if (iParam < 0) {
            throw new RuntimeException("Couldn't find the type variable " + var + " (offset " + iTypeVar + ") in the @" + Template.class.getName() + " annotation : " + Arrays.asList(values));
        }

        return iParam;
    }

    protected ClassTypeVariableExtractor createClassTypeVariableExtractor(TypeVariable<Class<?>> var) {
        final Class<?> typeClass = var.getGenericDeclaration();
        final int iTypeInParams = getAnnotatedTemplateTypeVariableIndexInArguments(var);
        return new ClassTypeVariableExtractor() {
            public Type extract(CPPObject instance) {
                typeClass.cast(instance);
                Object[] params = getTemplateParameters(instance, typeClass);
                if (params == null) {
                    throw new RuntimeException("No type parameters found in this instance : " + instance);
                }

                return (Type) params[iTypeInParams];
            }
        };
    }

    protected MethodTypeVariableExtractor createMethodTypeVariableExtractor(TypeVariable<?> var) {
        GenericDeclaration d = var.getGenericDeclaration();
        if (d instanceof Class) {
            final Class<?> typeClass = (Class<?>) d;
            final ClassTypeVariableExtractor ce = createClassTypeVariableExtractor((TypeVariable) var);
            return new MethodTypeVariableExtractor() {
                public Type extract(CPPObject instance, Object[] methodTemplateParameters) {
                    return ce.extract(instance);
                }
            };
        } else {
            Method method = (Method) d;
            final Class<?> typeClass = method.getDeclaringClass();

            final int iTypeInParams = getAnnotatedTemplateTypeVariableIndexInArguments(var);
            return new MethodTypeVariableExtractor() {
                public Type extract(CPPObject instance, Object[] methodTemplateParameters) {
                    typeClass.cast(instance);
                    return (Type) methodTemplateParameters[iTypeInParams];
                }
            };
        }
    }

    @Override
    public <T extends NativeObject> Class<? extends T> getActualInstanceClass(Pointer<T> pInstance, Type officialType) {
        //String className = null;
        // For C++ classes in general, take type info at offset -1 of vtable (if first field matches the address of a known static or dynamic virtual table) and use it to create the correct instance.
//		Pointer<?> vptr = pInstance.getPointer(0);
//		Symbol symbol = BridJ.getSymbolByAddress(vptr.getPeer());
//		if (symbol != null && symbol.isVirtualTable()) {
//			if (symbol.enclosingType.matches(officialType))
//				return officialType;
//			
//			try {
//				Class<?> type = BridJ.getCPPRuntime().getCPPClass(symbol.enclosingType);
//				if (officialType == null || officialType.isAssignableFrom(type))
//					return type;
//			} catch (ClassNotFoundException ex) {}
//			return officialType;
//			
//			/*long tinf = JNI.get_pointer(ptr - Pointer.SIZE);
//			symbol = BridJ.getSymbolByAddress(tinf);
//			if (symbol != null && symbol.isTypeInfo()) {
//				
//			}*/
//		}
        // For Objective-C classes, use "const char* className = class_getName([yourObject class]);" and match to registered classes or more
        // Bundle auto-generated type mappings files : bridj::CPPTest=org.bridj.test.cpp.CPPTest
        // 
        return Utils.getClass(officialType);
    }
    Map<Class<?>, Integer> virtualMethodsCounts = new HashMap<Class<?>, Integer>();

    public int getVirtualMethodsCount(Class<?> type) {
        Integer count = virtualMethodsCounts.get(type);
        if (count == null) {
            List<VirtMeth> mets = new ArrayList<VirtMeth>();
            listVirtualMethods(type, mets);

            // TODO unify this !
            virtualMethodsCounts.put(type, count = mets.size());
        }
        return count;
    }

    protected static class VirtMeth {

        Method implementation, definition;
    }

    protected void listVirtualMethods(Class<?> type, List<VirtMeth> out) {
        if (!CPPObject.class.isAssignableFrom(type)) {
            return;
        }

        Class<?> sup = type.getSuperclass();
        if (sup != CPPObject.class) {
            listVirtualMethods(sup, out);
        }

        int nParentMethods = out.size();

        Map<Integer, VirtMeth> newVirtuals = new TreeMap<Integer, VirtMeth>();

        methods:
        for (Method method : type.getDeclaredMethods()) {
            String methodName = getMethodName(method);
            Type[] methodParameterTypes = method.getGenericParameterTypes();
            for (int iParentMethod = 0; iParentMethod < nParentMethods; iParentMethod++) {
                VirtMeth pvm = out.get(iParentMethod);
                Method parentMethod = pvm.definition;
                if (parentMethod.getDeclaringClass() == type) {
                    continue; // was just added in the same listVirtualMethods call !
                }
                //if (parentMethod.getAnnotation(Virtual.class) == null)
                //    continue; // not a virtual method, too bad
                if (getMethodName(parentMethod).equals(methodName) && isOverridenSignature(parentMethod.getGenericParameterTypes(), methodParameterTypes, 0)) {
                    VirtMeth vm = new VirtMeth();
                    vm.definition = pvm.definition;
                    vm.implementation = method;
                    out.set(iParentMethod, vm);
                    continue methods;
                }
            }

            Virtual virtual = method.getAnnotation(Virtual.class);
            if (virtual != null) {
                VirtMeth vm = new VirtMeth();
                vm.definition = vm.implementation = method;
                newVirtuals.put(virtual.value(), vm);
            }
        }
        out.addAll(newVirtuals.values());
    }

    @Override
    protected void registerNativeMethod(Class<?> type, NativeLibrary typeLibrary, Method method, NativeLibrary methodLibrary, Builder builder, MethodCallInfoBuilder methodCallInfoBuilder) throws FileNotFoundException {

        int modifiers = method.getModifiers();
        boolean isCPPClass = CPPObject.class.isAssignableFrom(method.getDeclaringClass());

//		Annotation[][] anns = method.getParameterAnnotations();
        if (!isCPPClass) {
            super.registerNativeMethod(type, typeLibrary, method, methodLibrary, builder, methodCallInfoBuilder);
            return;
        }

        MethodCallInfo mci = methodCallInfoBuilder.apply(method);

        Virtual va = method.getAnnotation(Virtual.class);
        if (va == null) {
            Symbol symbol = methodLibrary.getSymbol(method);
            mci.setForwardedPointer(symbol == null ? 0 : symbol.getAddress());
            if (mci.getForwardedPointer() == 0) {
                assert error("Method " + method.toGenericString() + " is not virtual but its address could not be resolved in the library.");
                return;
            }
            if (Modifier.isStatic(modifiers)) {
                builder.addFunction(mci);
                if (debug) {
                    info("Registering " + method + " as function or static C++ method " + symbol.getName());
                }
            } else {
                builder.addFunction(mci);
                if (debug) {
                    info("Registering " + method + " as C++ method " + symbol.getName());
                }
            }
        } else {
            if (Modifier.isStatic(modifiers)) {
                warning("Method " + method.toGenericString() + " is native and maps to a function, but is not static.");
            }

            int theoreticalVirtualIndex = va.value();
            int theoreticalAbsoluteVirtualIndex = theoreticalVirtualIndex < 0 ? - 1 : getAbsoluteVirtualIndex(method, theoreticalVirtualIndex, type);

            int absoluteVirtualIndex;

            Pointer<Pointer<?>> pVirtualTable = isCPPClass && typeLibrary != null ? (Pointer) pointerToAddress(getVirtualTable(type, typeLibrary), Pointer.class) : null;
            if (pVirtualTable == null) {
                if (theoreticalAbsoluteVirtualIndex < 0) {
                    error("Method " + method.toGenericString() + " is virtual but the virtual table of class " + type.getName() + " was not found and the virtual method index is not provided in its @Virtual annotation.");
                    return;
                }
                absoluteVirtualIndex = theoreticalAbsoluteVirtualIndex;
            } else {
                int guessedAbsoluteVirtualIndex = getPositionInVirtualTable(pVirtualTable, method, typeLibrary);
                if (guessedAbsoluteVirtualIndex < 0) {
                    if (theoreticalAbsoluteVirtualIndex < 0) {
                        error("Method " + method.toGenericString() + " is virtual but its position could not be found in the virtual table.");
                        return;
                    } else {
                        absoluteVirtualIndex = theoreticalAbsoluteVirtualIndex;
                    }
                } else {
                    if (theoreticalAbsoluteVirtualIndex >= 0 && guessedAbsoluteVirtualIndex != theoreticalAbsoluteVirtualIndex) {
                        warning("Method " + method.toGenericString() + " has @Virtual annotation indicating virtual index " + theoreticalAbsoluteVirtualIndex + ", but analysis of the actual virtual table rather indicates it has index " + guessedAbsoluteVirtualIndex + " (using the guess)");
                    }
                    absoluteVirtualIndex = guessedAbsoluteVirtualIndex;
                }
            }
            mci.setVirtualIndex(absoluteVirtualIndex);
            if (debug) {
                info("Registering " + method.toGenericString() + " as virtual C++ method with absolute virtual table index = " + absoluteVirtualIndex);
            }
            builder.addVirtualMethod(mci);
        }
    }

    int getAbsoluteVirtualIndex(Method method, int virtualIndex, Class<?> type) {
        Class<?> superclass = type.getSuperclass();
        int virtualOffset = getVirtualMethodsCount(superclass);
        boolean isNewVirtual = true;
        if (superclass != null) {
            try {
                // TODO handle polymorphism in overloads :
                superclass.getMethod(getMethodName(method), method.getParameterTypes());
                isNewVirtual = false;
            } catch (NoSuchMethodException ex) {
            }
        }
        int absoluteVirtualIndex = isNewVirtual ? virtualOffset + virtualIndex : virtualIndex;
        return absoluteVirtualIndex;
    }

    public static class MemoryOperators {

        protected DynamicFunction<Pointer<?>> newFct;
        protected DynamicFunction<Pointer<?>> newArrayFct;
        protected DynamicFunction<Void> deleteFct;
        protected DynamicFunction<Void> deleteArrayFct;

        protected MemoryOperators() {
        }

        @SuppressWarnings({ "deprecation", "unchecked", "rawtypes" })
        public MemoryOperators(NativeLibrary library) {
            for (Symbol sym : library.getSymbols()) {
                try {
                    MemberRef parsedRef = sym.getParsedRef();
                    IdentLike n = parsedRef.getMemberName();

                    if (SpecialName.New.equals(n)) {
                        newFct = pointerToAddress(sym.getAddress()).asDynamicFunction(null, Pointer.class, SizeT.class);
                    } else if (SpecialName.NewArray.equals(n)) {
                        newFct = pointerToAddress(sym.getAddress()).asDynamicFunction(null, Pointer.class, SizeT.class);
                    } else if (SpecialName.Delete.equals(n)) {
                        newFct = pointerToAddress(sym.getAddress()).asDynamicFunction(null, Void.class, Pointer.class);
                    } else if (SpecialName.DeleteArray.equals(n)) {
                        newFct = pointerToAddress(sym.getAddress()).asDynamicFunction(null, Void.class, Pointer.class);
                    }

                } catch (Exception ex) {
                }
            }
        }

        public Pointer<?> cppNew(long size) {
            return (Pointer<?>)newFct.apply(new SizeT(size));
        }

        public Pointer<?> cppNewArray(long size) {
            return (Pointer<?>)newArrayFct.apply(new SizeT(size));
        }

        public void cppDelete(Pointer<?> ptr) {
            deleteFct.apply(ptr);
        }

        public void cppDeleteArray(Pointer<?> ptr) {
            deleteArrayFct.apply(ptr);
        }
    }
    volatile MemoryOperators memoryOperators;

    public synchronized MemoryOperators getMemoryOperators() {
        if (memoryOperators == null) {
            try {
                NativeLibrary libStdCpp = BridJ.getNativeLibrary("stdc++");
                memoryOperators = new MemoryOperators(libStdCpp);
            } catch (Exception ex) {
                BridJ.error(null, ex);
            }
        }
        return memoryOperators;
    }

    int getPositionInVirtualTable(Method method, NativeLibrary library) {
        Class<?> type = method.getDeclaringClass();
        @SuppressWarnings({ "unchecked", "deprecation" })
        Pointer<Pointer<?>> pVirtualTable = (Pointer)pointerToAddress(getVirtualTable(type, library), Pointer.class);
        return getPositionInVirtualTable(pVirtualTable, method, library);
    }

    public int getPositionInVirtualTable(Pointer<Pointer<?>> pVirtualTable, Method method, NativeLibrary library) {
        //Pointer<?> typeInfo = pVirtualTable.get(1);
        int methodsOffset = 0;//library.isMSVC() ? 0 : -2;///2;
        String className = getClassName(method.getDeclaringClass());
        for (int iVirtual = 0;; iVirtual++) {
            Pointer<?> pMethod = pVirtualTable.get(methodsOffset + iVirtual);
            String virtualMethodName = pMethod == null ? null : library.getSymbolName(pMethod.getPeer());
            //System.out.println("#\n# At index " + methodsOffset + " + " + iVirtual + " of vptr for class " + className + ", found symbol " + Long.toHexString(pMethod.getPeer()) + " = '" + virtualMethodName + "'\n#");
            if (virtualMethodName == null) {
                if (debug) {
                    info("\tVtable(" + className + ")[" + iVirtual + "] = null");
                }
            }
            if (pMethod == null) {
                return -1;
            } else if (virtualMethodName == null) {
                continue;
            }
            try {
                MemberRef mr = library.parseSymbol(virtualMethodName);
                if (debug) {
                    info("\tVtable(" + className + ")[" + iVirtual + "] = " + virtualMethodName + " = " + mr);
                }
                if (mr != null && mr.matchesSignature(method)) {
                    return iVirtual;
                } else if (library.isMSVC() && !mr.matchesEnclosingType(method)) {
                    break; // no NULL terminator in MSVC++ vtables, so we have to guess when we've reached the end
                }
            } catch (Demangler.DemanglingException ex) {
                BridJ.warning("Failed to demangle '" + virtualMethodName + "' during inspection of virtual table for '" + method.toGenericString() + "' : " + ex);
            }

        }
        return -1;
    }

    static int getDefaultDyncallCppConvention() {
        int convention = DC_CALL_C_DEFAULT;
        if (!Platform.is64Bits() && Platform.isWindows()) {
            convention = DC_CALL_C_X86_WIN32_THIS_MS;
        }
        return convention;
    }

    private String ptrToString(Pointer<?> ptr, NativeLibrary library) {
        return ptr == null ? "null" : Long.toHexString(ptr.getPeer()) + " (" + library.getSymbolName(ptr.getPeer()) + ")";
    }

    @Convention(Style.ThisCall)
    public abstract static class CPPDestructor<C extends CPPDestructor<C>> extends Callback<C> {

        public abstract void destroy(long peer);
    }
    Set<Type> typesThatDontNeedASyntheticVirtualTable = new HashSet<Type>();
    Map<Type, VTable> syntheticVirtualTables = new HashMap<Type, VTable>();

    @SuppressWarnings("deprecation")
    protected boolean installRegularVTablePtr(Type type, NativeLibrary library, Pointer<?> peer) {
        long vtablePtr = getVirtualTable(type, library);
        if (vtablePtr != 0) {
            if (BridJ.debug) {
                BridJ.info("Installing regular vtable pointer " + Pointer.pointerToAddress(vtablePtr) + " to instance at " + peer + " (type = " + Utils.toString(type) + ")");
            }
            peer.setSizeT(vtablePtr);
            return true;
        }
        return false;
    }

    protected boolean installSyntheticVTablePtr(Type type, NativeLibrary library, Pointer<?> peer) {
        synchronized (syntheticVirtualTables) {
            VTable vtable = syntheticVirtualTables.get(type);
            if (vtable == null) {
                if (!typesThatDontNeedASyntheticVirtualTable.contains(type)) {
                    List<VirtMeth> methods = new ArrayList<VirtMeth>();
                    listVirtualMethods(Utils.getClass(type), methods);
                    boolean needsASyntheticVirtualTable = false;
                    for (VirtMeth method : methods) {
                        if (!Modifier.isNative(method.implementation.getModifiers())) {
                            needsASyntheticVirtualTable = true;
                            break;
                        }
                    }
                    if (needsASyntheticVirtualTable) {
                        Type parentType = Utils.getParent(type);
                        @SuppressWarnings("rawtypes")
                        Pointer<Pointer> parentVTablePtr = null;
                        if (CPPObject.class.isAssignableFrom(Utils.getClass(parentType))) {
                            parentVTablePtr = peer.getPointer(Pointer.class);
                            if (BridJ.debug) {
                                BridJ.info("Found parent virtual table pointer = " + ptrToString(parentVTablePtr, library));
                                /*Pointer<Pointer> expectedParentVTablePtr = pointerToAddress(getVirtualTable(parentType, library), Pointer.class);
                                 if (expectedParentVTablePtr != null && !Utils.eq(parentVTablePtr, expectedParentVTablePtr))
                                 BridJ.warning("Weird parent virtual table pointer : expected " + ptrToString(expectedParentVTablePtr, library) + ", got " + ptrToString(parentVTablePtr, library));
                                 */

                            }
                            //parentVTablePtr = pointerToAddress(getVirtualTable(parentType, library), Pointer.class);
                        }
                        syntheticVirtualTables.put(type, vtable = synthetizeVirtualTable(type, parentVTablePtr, methods, library));
                    } else {
                        typesThatDontNeedASyntheticVirtualTable.add(type);
                    }
                }
            }
            if (vtable != null) {
                if (BridJ.debug) {
                    BridJ.info("Installing synthetic vtable pointer " + vtable.ptr + " to instance at " + peer + " (type = " + Utils.toString(type) + ", " + vtable.callbacks.size() + " callbacks)");
                }
                peer.setPointer(vtable.ptr);
                return vtable.ptr != null;
            } else {
                return false;
            }
        }
    }

    static class VTable {

        Pointer<Pointer<?>> ptr;
        Map<Method, Pointer<?>> callbacks = new HashMap<Method, Pointer<?>>();
    }

    @SuppressWarnings("rawtypes")
    protected VTable synthetizeVirtualTable(Type type, Pointer<Pointer> parentVTablePtr, List<VirtMeth> methods, NativeLibrary library) {
        int nMethods = methods.size();
        //Pointer<Pointer> parentVTablePtr = pointerToAddress(getVirtualTable(Utils.getParent(type), library), Pointer.class);
        VTable vtable = new VTable();
        vtable.ptr = allocatePointers(nMethods + 2).next(2); // leave two null pointers at index -2 and -1, to say there's no runtime type information available.

        Class<?> c = Utils.getClass(type);
        for (int iMethod = 0; iMethod < nMethods; iMethod++) {
            VirtMeth vm = methods.get(iMethod);
            Pointer<?> pMethod;
            if (Modifier.isNative(vm.implementation.getModifiers())) {
                pMethod = parentVTablePtr == null ? null : parentVTablePtr.get(iMethod);
            } else {
                try {
                    MethodCallInfo mci = new MethodCallInfo(vm.implementation, vm.definition);
                    mci.setDeclaringClass(vm.implementation.getDeclaringClass());
                    pMethod = createCToJavaCallback(mci, c);
                    vtable.callbacks.put(vm.implementation, pMethod);
                } catch (Throwable th) {
                    BridJ.error("Failed to register overridden method " + vm.implementation + " for type " + type + " (original method = " + vm.definition + ")", th);
                    pMethod = null;
                }
            }
            vtable.ptr.set(iMethod, (Pointer) pMethod);
        }
        return vtable;
    }

    static int getTemplateParametersCount(Class<?> typeClass) {
        Template t = typeClass.getAnnotation(Template.class);
        // TODO do something with these args !
        int templateParametersCount = t == null ? 0 : t.value().length;
        return templateParametersCount;
    }
    Map<Pair<Type, Integer>, DynamicFunction<?>> constructors = new HashMap<Pair<Type, Integer>, DynamicFunction<?>>();

    @SuppressWarnings({ "deprecation", "unchecked" })
    <R> DynamicFunction<R> getConstructor(final Class<?> typeClass, final Type type, NativeLibrary lib, int constructorId) {
        Pair<Type, Integer> key = new Pair<Type, Integer>(type, constructorId);
        DynamicFunction<R> constructor = (DynamicFunction<R>) constructors.get(key);
        if (constructor == null) {
            try {
                final Constructor<?> constr;
                try {
                    constr = findConstructor(typeClass, constructorId, true);

                    if (debug) {
                        BridJ.info("Found constructor for " + Utils.toString(type) + " : " + constr);
                    }
                } catch (NoSuchMethodException ex) {
                    if (debug) {
                        BridJ.info("No constructor for " + Utils.toString(type));
                    }
                    return null;
                }
                Symbol symbol = lib == null ? null : lib.getFirstMatchingSymbol(new SymbolAccepter() {
                    public boolean accept(Symbol symbol) {
                        return symbol.matchesConstructor(constr.getDeclaringClass() == Utils.getClass(type) ? type : constr.getDeclaringClass() /* TODO */, constr);
                    }
                });
                if (symbol == null) {
                    if (constructorId >= 0) {
                        throw new RuntimeException("No matching constructor for " + Utils.toString(type) + " (" + constr + ")");
                    } else if (debug) {
                        BridJ.info("No matching constructor for " + Utils.toString(type) + " (" + constr + ")");
                    }
                    return null;
                }

                if (debug) {
                    info("Registering constructor " + constr + " as " + symbol.getName());
                }

                // TODO do something with these args !
                int templateParametersCount = getTemplateParametersCount(typeClass);

                Class<?>[] consParamTypes = constr.getParameterTypes();
                Class<?>[] consThisParamTypes = new Class[consParamTypes.length + 1 - templateParametersCount];
                consThisParamTypes[0] = Pointer.class;
                System.arraycopy(consParamTypes, templateParametersCount, consThisParamTypes, 1, consParamTypes.length - templateParametersCount);

                DynamicFunctionFactory constructorFactory = getDynamicFunctionFactory(lib, Style.ThisCall, void.class, consThisParamTypes);

                constructor = (DynamicFunction<R>) constructorFactory.newInstance((Pointer<? extends NativeObject>)pointerToAddress(symbol.getAddress()));
                constructors.put(key, constructor);
            } catch (Throwable th) {
                th.printStackTrace();
                throw new RuntimeException("Unable to create constructor " + constructorId + " for " + type + " : " + th, th);
            }
        }
        return constructor;
    }
    Map<Type, CPPDestructor<?>> destructors = new HashMap<Type, CPPDestructor<?>>();

    @SuppressWarnings("deprecation")
    CPPDestructor<?> getDestructor(final Class<?> typeClass, Type type, NativeLibrary lib) {
        CPPDestructor<?> destructor = destructors.get(type);
        if (destructor == null) {
            Symbol symbol = lib.getFirstMatchingSymbol(new SymbolAccepter() {
                public boolean accept(Symbol symbol) {
                    return symbol.matchesDestructor(typeClass);
                }
            });
            if (BridJ.debug && symbol != null) {
                info("Registering destructor of " + Utils.toString(type) + " as " + symbol.getName());
            }

            if (symbol != null) {
                destructors.put(type, destructor = pointerToAddress(symbol.getAddress(), CPPDestructor.class).get());
            }
        }
        return destructor;
    }

    Pointer.Releaser newCPPReleaser(final Type type) {
        try {
            final Class<?> typeClass = Utils.getClass(type);
            NativeLibrary lib = BridJ.getNativeLibrary(typeClass);
            return newCPPReleaser(type, typeClass, lib);
        } catch (Throwable th) {
            throw new RuntimeException("Failed to create a C++ destructor for type " + Utils.toString(type) + " : " + th, th);
        }
    }

    Pointer.Releaser newCPPReleaser(final Type type, final Class<?> typeClass, NativeLibrary lib) throws FileNotFoundException {
        Pointer.Releaser releaser = null;
        //final Class<?> typeClass = Utils.getClass(type);
        //NativeLibrary lib = BridJ.getNativeLibrary(typeClass);
        if (lib != null && BridJ.enableDestructors) {
            final CPPDestructor<?> destructor = getDestructor(typeClass, type, lib);
            if (destructor != null) {
                releaser = new Pointer.Releaser() { //@Override 
                    public void release(Pointer<?> p) {
                        if (BridJ.debug) {
                            BridJ.info("Destructing instance of C++ type " + Utils.toString(type) + " (address = " + p + ", destructor = " + getPointer(destructor) + ")");
                        }

                        //System.out.println("Destructing instance of C++ type " + type + "...");
                        long peer = p.getPeer();
                        destructor.destroy(peer);
                        BridJ.setJavaObjectFromNativePeer(peer, null);
                    }
                };
            }
        }
        return releaser;
    }

    protected <T extends CPPObject> Pointer<T> newCPPInstance(T instance, final Type type, int constructorId, Object... args) {
        Pointer<T> peer = null;
        try {
            final Class<T> typeClass = Utils.getClass(type);
            NativeLibrary lib = BridJ.getNativeLibrary(typeClass);

            if (BridJ.debug) {
                info("Creating C++ instance of type " + type + " with args " + Arrays.asList(args));
            }
            Pointer.Releaser releaser = newCPPReleaser(type, typeClass, lib);

            long size = sizeOf(type, null);
            peer = (Pointer) Pointer.allocateBytes(PointerIO.getInstance(type), size, releaser).as(type);

            DynamicFunction<?> constructor = constructorId == SKIP_CONSTRUCTOR ? null : getConstructor(typeClass, type, lib, constructorId);

            if (lib != null && CPPObject.class.isAssignableFrom(typeClass)) {
                installRegularVTablePtr(type, lib, peer);
            } else {
                // TODO ObjCObject : call alloc on class type !!
            }

            // Calling the constructor with the non-template parameters :
            if (constructor != null) {
                Object[] consThisArgs = new Object[1 + args.length];
                consThisArgs[0] = peer;
                System.arraycopy(args, 0, consThisArgs, 1, args.length);

                constructor.apply(consThisArgs);
            }

            // Install synthetic virtual table and associate the Java instance to the corresponding native pointer : 
            if (CPPObject.class.isAssignableFrom(typeClass)) {
                if (installSyntheticVTablePtr(type, lib, peer)) {
                    BridJ.setJavaObjectFromNativePeer(peer.getPeer(), instance);
                }
            } else {
                // TODO ObjCObject : call alloc on class type !!
            }
            return peer;
        } catch (Exception ex) {
            ex.printStackTrace();
            if (peer != null) {
                peer.release();
            }
            throw new RuntimeException("Failed to allocate new instance of type " + type, ex);
        }
    }
    /*
     Map<Type, Pointer<Pointer<?>>> vtablePtrs = new HashMap<Type, Pointer<Pointer<?>>>();
     @SuppressWarnings("unchecked")
     public
     //Pointer<Pointer<?>>
     long getVirtualTable(Type type, NativeLibrary library) {
     Pointer<Pointer<?>> p = vtablePtrs.get(type);
     if (p == null) {
     Class<?> typeClass = Utils.getClass(type);
     // TODO ask for real template name
     String className = typeClass.getSimpleName();
     String vtableSymbol;
     if (Platform.isWindows())
     vtableSymbol = "??_7" + className + "@@6B@";
     else
     vtableSymbol = "_ZTV" + className.length() + className;

     long addr = library.getSymbolAddress(vtableSymbol);
     //long addr = JNI.findSymbolInLibrary(getHandle(), vtableSymbolName);
     //			System.out.println(TestCPP.hex(addr));
     //			TestCPP.print(type.getName() + " vtable", addr, 5, 2);
        	
     p = (Pointer)Pointer.pointerToAddress(addr, Pointer.class);
     vtablePtrs.put(type, p);
     }
     return p.getPeer();
     }*/
    Map<Type, Long> vtables = new HashMap<Type, Long>();

    long getVirtualTable(Type type, NativeLibrary library) {
        Long vtable = vtables.get(type);
        if (vtable == null) {
            final Class<?> typeClass = Utils.getClass(type);
            if (false) {
                String className = getClassName(typeClass);
                String vtableSymbol;
                if (Platform.isWindows()) {
                    vtableSymbol = "??_7" + className + "@@6B@";
                } else {
                    vtableSymbol = "_ZTV" + className.length() + className;
                }

                vtables.put(type, vtable = library.getSymbolAddress(vtableSymbol));
            } else {
                Symbol symbol = library.getFirstMatchingSymbol(new SymbolAccepter() {
                    public boolean accept(Symbol symbol) {
                        return symbol.matchesVirtualTable(typeClass);
                    }
                });
                if (symbol != null) {
                    if (BridJ.debug) {
                        info("Registering vtable of " + Utils.toString(type) + " as " + symbol.getName());
                    }
//                    Pointer<Pointer> pp = pointerToAddress(symbol.getAddress(), Pointer.class);
//                    
//                    for (int i = 0; i < 6; i++) {
//                        Pointer p = pp.get(i);
////                        if (p == null)
////                            break;
//                        String n = p == null ? null : library.getSymbolName(p.getPeer());
//                        info("\tVtable entry " + i + " = " + p + " (" + n + ")");
////                        if (n == null)
////                            break;
//                    }
                } else if (getVirtualMethodsCount(typeClass) > 0 && warnAboutMissingVTables()) {
                    error("Failed to find a vtable for type " + Utils.toString(type));
                }

                if (symbol != null) {
                    long address = symbol.getAddress();
                    vtable = library.isMSVC() ? address : address + 2 * Pointer.SIZE;
                } else {
                    vtable = 0L;
                }
                vtables.put(type, vtable);//*/
            }
        }
        return vtable;
    }

    protected boolean warnAboutMissingVTables() {
        return true;
    }

    @Override
    protected boolean shouldWarnIfNoFieldsInStruct() {
        return false;
    }

    public class CPPTypeInfo<T extends CPPObject> extends CTypeInfo<T> {

        protected final int typeParamCount;
        protected Object[] templateParameters;

        public CPPTypeInfo(Type type) {
            super(type);
            Class<?> typeClass = Utils.getClass(type);
            typeParamCount = typeClass.getTypeParameters().length;
            if (typeParamCount > 0 && !(type instanceof ParameterizedType)) {
                throw new RuntimeException("Class " + typeClass.getName() + " takes type parameters");
            }
            templateParameters = getTemplateParameters(type);
        }
        Map<TypeVariable<Class<?>>, ClassTypeVariableExtractor> classTypeVariableExtractors;
        Map<TypeVariable<?>, MethodTypeVariableExtractor> methodTypeVariableExtractors;

        @Override
        protected T newCastInstance() throws NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
            if (templateParameters.length == 0) {
                return super.newCastInstance();
            }

            Class<?> cc = getCastClass();
            for (Constructor c : cc.getConstructors()) {
                if (Utils.parametersComplyToSignature(templateParameters, c.getParameterTypes())) {
                    c.setAccessible(true);
                    T instance = (T) c.newInstance(templateParameters);
//                    setTemplateParameters(instance, typeClass, templateParameters);
                    return instance;
                }
            }
            throw new RuntimeException("Failed to find template constructor in class " + cc.getName());
        }

        public Type resolveClassType(CPPObject instance, TypeVariable<?> var) {
            return getClassTypeVariableExtractor((TypeVariable) var).extract(instance);
        }

        public Type resolveMethodType(CPPObject instance, Object[] methodTemplateParameters, TypeVariable<?> var) {
            return getMethodTypeVariableExtractor(var).extract(instance, methodTemplateParameters);
        }

        protected synchronized ClassTypeVariableExtractor getClassTypeVariableExtractor(TypeVariable<Class<?>> var) {
            if (classTypeVariableExtractors == null) {
                classTypeVariableExtractors = new HashMap<TypeVariable<Class<?>>, ClassTypeVariableExtractor>();
            }
            ClassTypeVariableExtractor e = classTypeVariableExtractors.get(var);
            if (e == null) {
                classTypeVariableExtractors.put(var, e = createClassTypeVariableExtractor(var));
            }
            return e;
        }

        protected synchronized MethodTypeVariableExtractor getMethodTypeVariableExtractor(TypeVariable<?> var) {
            if (methodTypeVariableExtractors == null) {
                methodTypeVariableExtractors = new HashMap<TypeVariable<?>, MethodTypeVariableExtractor>();
            }
            MethodTypeVariableExtractor e = methodTypeVariableExtractors.get(var);
            if (e == null) {
                methodTypeVariableExtractors.put(var, e = createMethodTypeVariableExtractor(var));
            }
            return e;
        }

        @Override
        public long sizeOf() {
            // TODO handle template size here ? (depends on template args)
            return super.sizeOf();
        }

        @Override
        public T createReturnInstance() {
            try {
                T instance = newCastInstance();
                initialize(instance, SKIP_CONSTRUCTOR, templateParameters);
                //setTemplateParameters(instance, typeClass, getTemplateParameters(type));
                return instance;
            } catch (Throwable th) {
                throw new RuntimeException("Failed to create a return instance for type " + Utils.toString(type) + " : " + th, th);
            }
        }

        @Override
        public T cast(Pointer peer) {
            if (BridJ.isCastingNativeObjectReturnTypeInCurrentThread()) {
                peer = peer.withReleaser(newCPPReleaser(type));
            }
            T instance = super.cast(peer);
            setTemplateParameters(instance, (Class) typeClass, templateParameters);
            return instance;
        }

        @Override
        public void initialize(T instance, Pointer<?> peer) {
            setTemplateParameters(instance, typeClass, templateParameters);
            super.initialize(instance, peer);
        }

        @SuppressWarnings("unchecked")
        @Override
        public void initialize(T instance, int constructorId, Object... targsAndArgs) {
            if (instance instanceof CPPObject) {
                CPPObject cppInstance = (CPPObject) instance;
                //instance.peer = allocate(instance.getClass(), constructorId, args);
                int[] position = new int[]{0};

                //TODO
                //Type cppType = CPPType.parseCPPType(CPPType.cons((Class<? extends CPPObject>)typeClass, targsAndArgs), position);
//                assert Arrays.asList(templateParameters).equals(Arrays.asList(takeLeft(targsAndArgs, typeParamCount)));
//                Object[] args = takeRight(targsAndArgs, targsAndArgs.length - typeParamCount);
                Type cppType = type;
                setTemplateParameters(instance, (Class) typeClass, templateParameters);
                //int actualArgsOffset = position[0] - 1, nActualArgs = args.length - actualArgsOffset;
                //System.out.println("actualArgsOffset = " + actualArgsOffset);
                //Object[] actualArgs = new Object[nActualArgs];
                //System.arraycopy(args, actualArgsOffset, actualArgs, 0, nActualArgs);

                setNativeObjectPeer(instance, newCPPInstance((CPPObject) instance, cppType, constructorId, targsAndArgs));
                super.initialize(instance, DEFAULT_CONSTRUCTOR);
            } else {
                super.initialize(instance, constructorId, targsAndArgs);
            }
        }

        @Override
        public T clone(T instance) throws CloneNotSupportedException {
            if (instance instanceof CPPObject) {
                // TODO use copy constructor !!!
            }
            return super.clone(instance);
        }

        @Override
        public void destroy(T instance) {
            //TODO call destructor here ? (and call here from finalizer manually created by autogenerated classes
        }

        private Object[] getTemplateParameters(Type type) {
            if (!(type instanceof CPPType)) {
                return new Object[0];
            }
            return ((CPPType) type).getTemplateParameters();
        }
    }
    /// Needs not be fast : TypeInfo will be cached in BridJ anyway !

    @Override
    public <T extends NativeObject> TypeInfo<T> getTypeInfo(final Type type) {
        return new CPPTypeInfo(type);
    }

    @Override
    public Type getType(Class<?> cls, Object[] targsAndArgs, int[] typeParamCount) {
        Template tpl = cls.getAnnotation(Template.class);
        int targsCount = tpl != null ? tpl.value().length : cls.getTypeParameters().length;
        if (typeParamCount != null) {
            assert typeParamCount.length == 1;
            typeParamCount[0] = targsCount;
        }
        return new CPPType(cls, takeLeft(targsAndArgs, targsCount));
    }

    public <T extends CPPObject> CPPTypeInfo<T> getCPPTypeInfo(final Type type) {
        return (CPPTypeInfo) getTypeInfo(type);
    }
}
