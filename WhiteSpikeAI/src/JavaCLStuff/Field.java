package JavaCLStuff;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface Field {

    /**
     * Index of the field in a struct (first field has index 0).<br>
     * Fields of parent structures must be taken into account (if parent struct
     * has 2 fields, first field of sub-struct has index 2).<br>
     * If more than one field are given the same index, this will produce an
     * union at that index.
     */
    int value();

    /**
     * Absolute index of the field from the start of the struct
     */
    //int offset() default Integer.MIN_VALUE;
    /**
     * Declare that this field shares its space with another (the two or more
     * fields are in an union).<br>
     * The unionWith index must be the index of the first field of the union.
     */
    int unionWith() default -1;
}
