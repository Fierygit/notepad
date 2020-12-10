package basic.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/25 21:05
 * @Target 注释标记另一个注释，以限制可以应用注释的Java元素类型。目标注释指定以下元素类型之一作为其值
 * <p>
 * ElementType.TYPE 可以应用于类的任何元素。
 * ElementType.FIELD 可以应用于字段或属性。
 * ElementType.METHOD 可以应用于方法级注释。
 * ElementType.PARAMETER 可以应用于方法的参数。
 * ElementType.CONSTRUCTOR 可以应用于构造函数。
 * ElementType.LOCAL_VARIABLE 可以应用于局部变量。
 * ElementType.ANNOTATION_TYPE 可以应用于注释类型。
 * ElementType.PACKAGE 可以应用于包声明。
 * ElementType.TYPE_PARAMETER
 * ElementType.TYPE_USE
 */

@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Before {
    String value();
}
