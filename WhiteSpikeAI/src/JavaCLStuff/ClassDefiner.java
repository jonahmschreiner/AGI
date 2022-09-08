package JavaCLStuff;

public interface ClassDefiner {

    Class<?> defineClass(String className, byte[] data) throws ClassFormatError;
}
