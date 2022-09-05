package JavaCLStuff;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specify the name of the native library that should be bound to the class or
 * method this annotation is put on.<br>
 * This name can then be changed at runtime to match the platform-specific name
 * of the library with
 * {@link org.bridj.BridJ#setNativeLibraryActualName(String, String)}.
 * <p>
 * Alternative aliases can also be added at runtime with
 * {@link org.bridj.BridJ#addNativeLibraryAlias(String, String)}.
 * <p>
 * Finally, dependencies can also be added at runtime with
 * {@link org.bridj.BridJ#addNativeLibraryDependencies(java.lang.String, java.lang.String...)}.
 * 
 * @author Olivier Chafik
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface Library {

    /**
     * Name of this library.
     */
    String value();

    /**
     * Names of libraries that need to be loaded before this library is loaded
     */
    String[] dependencies() default {};

    String versionPattern() default "";
}
