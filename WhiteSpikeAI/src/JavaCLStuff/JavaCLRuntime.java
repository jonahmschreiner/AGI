package JavaCLStuff;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import JavaCLStuff.BridJRuntime;

/**
 * Specify the runtime that should be used to bind native methods (default is
 * {@link org.bridj.CRuntime} if no annotation is provided).
 * <br>
 * Also see {@link org.bridj.BridJ#register()}.
 * @author Olivier
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JavaCLRuntime {

    Class<? extends BridJRuntime>
            value();
}
