package JavaCLStuff;



public class TypedPointer<T> extends Pointer.OrderedPointer<T> {

    Pointer<?> copy;

    private TypedPointer(PointerIO<T> io, long peer) {
        super(io, peer, UNKNOWN_VALIDITY, UNKNOWN_VALIDITY, null, 0, null);
    }

    public TypedPointer(long address) {
        this(PointerIO.<T>getPointerInstance(), address);
    }

    public TypedPointer(Pointer<T> ptr) {
        this(PointerIO.<T>getPointerInstance(), ptr.getPeer());
        copy = ptr;
    }
}
