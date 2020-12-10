package basic.AOP;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/25 21:15
 */

public class Proxy implements InvocationHandler {

    private final Class<?> apect;// 切面类


    public Proxy(Class<?> apect) {
        this.apect = apect;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Method[] methods = apect.getMethods();
        for(Method m : methods){
            Before annotation = m.getAnnotation(Before.class);
            if(annotation != null){
                m.invoke(this.apect.newInstance());
            }
        }

        method.invoke(proxy, args);

        return null;
    }
}