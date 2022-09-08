package JavaCLStuff;


import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import JavaCLStuff.StructCustomizer;


@SuppressWarnings("deprecation")
@Target({ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Struct {

    int pack() default -1;
    //int padding() default -1;

    int fieldCount() default -1;

    int size() default -1;

    Class<? extends StructCustomizer> customizer() default StructCustomizer.class;
}
