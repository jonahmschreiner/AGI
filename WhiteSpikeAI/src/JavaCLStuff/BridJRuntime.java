package JavaCLStuff;

import java.lang.reflect.Type;

public interface BridJRuntime {
    public interface TypeInfo<T extends NativeObject> {

        T cast(Pointer<?> peer);

        void initialize(T instance);

        void initialize(T instance, Pointer<?> peer);

        void initialize(T instance, int constructorId, Object... args);

        void destroy(T instance);

        T createReturnInstance();

        T clone(T instance) throws CloneNotSupportedException;

        BridJRuntime getRuntime();

        Type getType();

        boolean equal(T instance, T other);

        int compare(T instance, T other);

        long sizeOf();

        void writeToNative(T instance);

        String describe(T instance);

        String describe();

        void readFromNative(T instance);

        void copyNativeObjectToAddress(T instance, Pointer<T> ptr);
    }

    Type getType(NativeObject instance);

    void register(Type type);

    void unregister(Type type);

    <T extends NativeObject> TypeInfo<T> getTypeInfo(final Type type);

    Type getType(final Class<?> cls, Object[] targs, int[] typeParamCount);

    boolean isAvailable();

    <T extends NativeObject> Class<? extends T> getActualInstanceClass(Pointer<T> pInstance, Type officialType);
}
