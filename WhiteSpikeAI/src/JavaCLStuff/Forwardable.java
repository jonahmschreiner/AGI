package JavaCLStuff;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * For annotations that can be forwarded to other annotations.
 * <p>
 * E.g. {@code @Ptr} can be forwarded to {@code @MyPtr} if the MyPtr annotation
 * class is annotated with {@code @Ptr }
 *
 * @author ochafik
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface Forwardable {
}
