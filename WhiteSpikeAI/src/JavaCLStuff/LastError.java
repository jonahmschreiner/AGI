package JavaCLStuff;



//import static org.bridj.LastError.Windows.*;
//import static org.bridj.LastError.Unix.*;

public class LastError extends NativeError {
  private static final long serialVersionUID = 1L;
  
		final int code, kind;
    String description;
    
    static final int eLastErrorKindWindows = 1, eLastErrorKindCLibrary = 2;

    LastError(int code, int kind) {
        super(null);
        this.code = code;
        this.kind = kind;
        /*
        if (BridJ.verbose) {
            BridJ.info("Last error detected : " + getMessage());
        }*/
    }

    public int hashCode() {
    	return Integer.valueOf(code).hashCode() ^ Integer.valueOf(kind).hashCode();
    }

    public boolean equals(Object o) {
    	if (!(o instanceof LastError)) {
    		return false;
    	}
    	LastError e = (LastError) o;
    	return code == e.code && kind == e.kind;
    }

    /**
     * Native error code (as returned by <a
     * href="http://en.wikipedia.org/wiki/Errno.h">errno</a> or <a
     * href="http://msdn.microsoft.com/en-us/library/ms679360(v=vs.85).aspx">GetLastError()</a>).
     */
    public int getCode() {
        return code;
    }

    /**
     * Native error description (as returned by <a
     * href="http://www.cplusplus.com/reference/clibrary/cstring/strerror/">strerror</a>
     * or <a
     * href="http://msdn.microsoft.com/en-us/library/ms680582(v=vs.85).aspx">FormatMessage</a>).
     */
    public String getDescription() {
    	if (description == null) {
    		description = getDescription(code, kind);
    	}
    	return description;
    }
    
    @Override
    public String getMessage() {
    	String description = getDescription();
    	return (description == null ? "?" : description.trim()) + " (error code = " + code + ")";
    }
    
    private static native String getDescription(int value, int kind);

    private static final ThreadLocal<LastError> lastError = new ThreadLocal<LastError>();

    public static LastError getLastError() {
        return lastError.get();
    }
    
    static LastError setLastError(int code, int kind) {
    		if (code == 0) {
    				lastError.set(null);
            return null;
        }
        LastError err = new LastError(code, kind);
        err.fillInStackTrace();
        lastError.set(err);
        return err;
    }
}
