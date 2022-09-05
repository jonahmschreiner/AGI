package JavaCLStuff;


import java.io.IOException;

import JavaCLStuff.ClassDefiner;

class PlatformSupport {

    PlatformSupport() {
    }

    public ClassDefiner getClassDefiner(ClassDefiner defaultDefiner, ClassLoader parentClassLoader) {
        return defaultDefiner;
    }
    private final static PlatformSupport instance;

    static {
        PlatformSupport _instance = null;
        if (Platform.isAndroid()) {
            try {
                _instance = (PlatformSupport) Class.forName("org.bridj.AndroidSupport").newInstance();;
            } catch (Exception ex) {
                throw new RuntimeException("Failed to instantiate the Android support class... Was the BridJ jar tampered with / trimmed too much ?", ex);
            }
        }

        if (_instance == null) {
            _instance = new PlatformSupport();
        }

        instance = _instance;
    }

    public static PlatformSupport getInstance() {
        return instance;
    }

    public NativeLibrary loadNativeLibrary(String name) throws IOException {
        return null;
    }
}
