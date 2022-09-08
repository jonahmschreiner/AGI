package JavaCLStuff;


import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import JavaCLStuff.CRuntime.MethodCallInfoBuilder;

/**
 * Factory that is able to create dynamic functions bindings with a given
 * signature
 */
public class DynamicFunctionFactory {

    final Constructor<? extends DynamicFunction<?>> constructor;
    final Method method;
    final long callbackHandle;

    @SuppressWarnings("deprecation")
    DynamicFunctionFactory(Class<? extends DynamicFunction<?>> callbackClass, Method method, /*Convention.Style style,*/ MethodCallInfoBuilder methodCallInfoBuilder) {
        try {
            this.constructor = callbackClass.getConstructor();
            this.method = method;

            MethodCallInfo mci = methodCallInfoBuilder.apply(method);
            callbackHandle = JNI.bindJavaToCCallbacks(mci);
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException("Failed to instantiate callback" + " : " + th, th);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() throws Throwable {
        if (BridJ.debugNeverFree) {
            return;
        }

        JNI.freeJavaToCCallbacks(callbackHandle, 1);
    }

    public <R> DynamicFunction<R> newInstance(Pointer<? extends NativeObject> functionPointer) {
        if (functionPointer == null) {
            return null;
        }

        try {
            @SuppressWarnings("unchecked")
            DynamicFunction<R> dcb = (DynamicFunction<R>) constructor.newInstance();
            dcb.peer = functionPointer;
            dcb.method = method;
            dcb.factory = this;

            return dcb;
        } catch (Throwable th) {
            th.printStackTrace();
            throw new RuntimeException("Failed to instantiate callback" + " : " + th, th);
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + method + ")";
    }
}
