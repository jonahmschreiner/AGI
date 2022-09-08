package JavaCLStuff;

public abstract class NativeObject implements NativeObjectInterface {

    protected Pointer<? extends NativeObject> peer;
    protected BridJRuntime.TypeInfo<NativeObject> typeInfo;

    protected NativeObject(Pointer<? extends NativeObject> peer, Object... targs) {
        BridJ.initialize(this, peer, targs);
    }

    protected NativeObject() {
        BridJ.initialize(this);
    }

    protected NativeObject(int constructorId, Object... args) {
        BridJ.initialize(this, constructorId, args);
    }

    public NativeObject clone() throws CloneNotSupportedException {
        return BridJ.clone(this);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NativeObject)) {
            return false;
        }

        return typeInfo.equal(this, (NativeObject) o);
    }
}
