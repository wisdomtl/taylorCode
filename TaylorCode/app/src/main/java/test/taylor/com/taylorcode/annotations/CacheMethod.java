package test.taylor.com.taylorcode.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * annotation case2:annotate method
 * define an method annotation
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheMethod {
    boolean isCache() default true ;
}
