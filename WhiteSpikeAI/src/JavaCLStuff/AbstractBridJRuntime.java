package JavaCLStuff;

import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

import JavaCLStuff.Utils;

public abstract class AbstractBridJRuntime implements BridJRuntime {
    //@Override

    public void unregister(Type type) {
        // TODO !!!
    }

    //@Override
    public Type getType(NativeObject instance) {
        if (instance == null) {
            return null;
        }
        return Utils.getClass(instance.getClass());
    }

    protected Constructor<?> findConstructor(Class<?> type, int constructorId, boolean onlyWithAnnotation) throws SecurityException, NoSuchMethodException {
        for (Constructor<?> c : type.getDeclaredConstructors()) {
            JavaCLStuff.Constructor ca = c.getAnnotation(JavaCLStuff.Constructor.class);
            if (ca == null) {
                continue;
            }
            if (ca.value() == constructorId) {
                return c;
            }
        }
        if (constructorId < 0)// && args.length == 0)
        {
            return type.getConstructor();
        }
        Class<?> sup = type.getSuperclass();
        if (sup != null) {
            try {
                Constructor<?> c = findConstructor(sup, constructorId, onlyWithAnnotation);
                if (onlyWithAnnotation && c != null) {
                    return c;
                }

                Type[] params = c.getGenericParameterTypes();
                Constructor<?>[] ccs = type.getDeclaredConstructors();
                for (Constructor<?> cc : ccs) {
                    Type[] ccparams = cc.getGenericParameterTypes();
                    int overrideOffset = Utils.getEnclosedConstructorParametersOffset(cc);
                    if (isOverridenSignature(params, ccparams, overrideOffset)) {
                        return cc;
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        throw new NoSuchMethodException("Cannot find constructor with index " + constructorId);
    }

    public static boolean isOverridenSignature(Type[] parentSignature, Type[] overrideSignature, int overrideOffset) {
        int n = parentSignature.length;
        if (overrideSignature.length - overrideOffset != n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            if (!isOverride(parentSignature[i], overrideSignature[overrideOffset + i])) {
                return false;
            }
        }
        return true;
    }

    protected static boolean isOverride(Type parentSignature, Type overrideSignature) {
        return Utils.getClass(parentSignature).isAssignableFrom(Utils.getClass(overrideSignature));
    }
}
