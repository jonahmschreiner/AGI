package JavaCLStuff;


import static JavaCLStuff.Utils.getUniqueParameterizedTypeParameter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

import JavaCLStuff.DyncallLibrary.DCstruct;

interface CallIO {

    Object newInstance(long address);

    long getDCStruct();

    long getPeer(Object o);

    public static class Utils {

        public static CallIO createPointerCallIOToTargetType(Type targetType) {
            return new CallIO.GenericPointerHandler(targetType);
        }

        public static <EE extends Enum<EE> & ValuedEnum<EE>> CallIO createValuedEnumCallIO(final Class<EE> enumClass) {
            return new CallIO() {
                @SuppressWarnings("unchecked")
                public Object newInstance(long value) {
                    return FlagSet.fromValue(value, enumClass);
                }

                public long getDCStruct() {
                    return 0;
                }

                public long getPeer(Object o) {
                    return 0;
                }
            };
        }
        //static final Map<Type, CallIO> instances = new HashMap<Type, CallIO>();

        public static /*synchronized*/ CallIO createPointerCallIO(Type type) {
            //CallIO io = instances.get(type);
            //if (io == null)
            //    instances.put(type, io = )
            return createPointerCallIO(JavaCLStuff.Utils.getClass(type), type);
        }

        @SuppressWarnings("unchecked")
        public static CallIO createPointerCallIO(Class<?> cl, Type type) {
            if (cl == Pointer.class) {
                return createPointerCallIOToTargetType(getUniqueParameterizedTypeParameter(type));
            }

            assert TypedPointer.class.isAssignableFrom(cl);
            return new CallIO.TypedPointerIO(((Class<? extends TypedPointer<?>>) cl));
        }
    }

    public static class TypedPointerIO implements CallIO {

        Class<? extends TypedPointer<?>> type;
        Constructor<?> constructor;

        public TypedPointerIO(Class<? extends TypedPointer<?>> type) {
            this.type = type;
            try {
                this.constructor = type.getConstructor(long.class);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to create " + CallIO.class.getName() + " for type " + type.getName(), ex);
            }
        }
        //@Override

        public Pointer<?> newInstance(long address) {
            try {
                return address == 0 ? null : (Pointer<?>) constructor.newInstance(address);
            } catch (Exception ex) {
                throw new RuntimeException("Failed to instantiate pointer of type " + type.getName(), ex);
            }
        }

        public long getDCStruct() {
            return 0;
        }

        public long getPeer(Object o) {
            return Pointer.getPeer((Pointer<?>) o);
        }
    }

    public static class NativeObjectHandler implements CallIO {

        final Class<? extends NativeObject> nativeClass;
        final Type nativeType;
        final Pointer<DCstruct> pStruct;

        public NativeObjectHandler(Class<? extends NativeObject> type, Type t, Pointer<DCstruct> pStruct) {
            this.nativeClass = type;
            this.nativeType = t;
            this.pStruct = pStruct;
        }
        //@Override

        @SuppressWarnings("deprecation")
        public NativeObject newInstance(long address) {
            return Pointer.pointerToAddress(address).getNativeObject(nativeClass);
        }

        public long getDCStruct() {
            return Pointer.getPeer(pStruct);
        }

        public long getPeer(Object o) {
            return Pointer.getAddress((NativeObject) o, nativeClass);
        }
    }
    public static final class GenericPointerHandler implements CallIO {

        @SuppressWarnings("unused")
        private final Type targetType;
        private final PointerIO<?> pointerIO;
        public GenericPointerHandler(Type targetType) {
            this.targetType = targetType;
            this.pointerIO = PointerIO.getInstance(targetType);
        }
        //@Override

        public Pointer<?> newInstance(long address) {
            return Pointer.pointerToAddress(address, pointerIO);
        }

        public long getDCStruct() {
            return 0;
        }

        public long getPeer(Object o) {
            return Pointer.getPeer((Pointer<?>) o);
        }
    }
}
