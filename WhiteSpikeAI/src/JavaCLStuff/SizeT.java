package JavaCLStuff;

public final class SizeT extends AbstractIntegral {

    public static final int SIZE = Platform.SIZE_T_SIZE;
    public static final SizeT ZERO = new SizeT(0), ONE = new SizeT(1);
    private static final long serialVersionUID = 1547942367767922396L;

    public SizeT(long value) {
        super(value);
    }

    public int byteSize() {
    	return SIZE;
    }

    public static SizeT valueOf(long value) {
        if (value == 0) {
            return ZERO;
        }
        if (value == 1) {
            return ONE;
        }
        return new SizeT(value);
    }
}
