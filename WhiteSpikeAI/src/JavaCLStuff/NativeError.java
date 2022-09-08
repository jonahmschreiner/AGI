package JavaCLStuff;

public abstract class NativeError extends Error {
  private static final long serialVersionUID = 1L;

		protected NativeError(String message) {
        super(message);
    }

    static String toHex(long address) {
        return "0x" + Long.toHexString(address);
    }
}
