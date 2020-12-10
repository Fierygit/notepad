package basic.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {  ElementType.TYPE ,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

	String columnName();

	String type();

	int length();

}
