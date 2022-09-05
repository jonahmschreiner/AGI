package JavaCLStuff;


import java.lang.reflect.Method;

public abstract class DynamicFunction<R> extends Callback<DynamicFunction<R>> {
    /// Don't GC the factory, which holds the native callback handle

    DynamicFunctionFactory factory;
    Method method;

    protected DynamicFunction() {
    }

    @SuppressWarnings("unchecked")
    public R apply(Object... args) {
        try {
            return (R) method.invoke(this, args);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException("Failed to invoke callback" + " : " + th, th);
        }
    }

    @Override
    public String toString() {
        return method.toString();
    }
}
