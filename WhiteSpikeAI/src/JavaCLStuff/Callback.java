package JavaCLStuff;


import JavaCLStuff.JavaCLRuntime;


@JavaCLRuntime(CRuntime.class)
public abstract class Callback<C extends Callback<C>> extends NativeObject implements CallbackInterface {

    @SuppressWarnings("unchecked")
    public Pointer<C> toPointer() {
        return Pointer.getPointer((C)this);
    }
}