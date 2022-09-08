package JavaCLStuff;

public final class CLong extends AbstractIntegral {

    public static final int SIZE = Platform.CLONG_SIZE;
    private static final long serialVersionUID = 1542942327767932396L;

    public CLong(long value) {
        super(value);
    }

    @Override
    public int byteSize() {
    	return SIZE;
    }

    public static CLong valueOf(long value) {
        return new CLong(value);
    }
}
