package basic.AOP;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/25 21:07
 */

@Target(value = ElementType.TYPE)
public @interface Aspect {
}
