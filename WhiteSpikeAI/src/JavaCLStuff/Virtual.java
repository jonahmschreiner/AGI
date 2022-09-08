package JavaCLStuff;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Virtual {

    /**
     * Optional relative virtual table offset for the C++ method (starts at 0
     * for each C++ class, even if it has ancestors with virtual methods)
     */
    int value() default -1;
}
