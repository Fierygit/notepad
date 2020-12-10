package basic.DesignPattern.structural.JavaDynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 12:28
 */

public class Proxy implements InvocationHandler {

    /**
     * @author Firefly
     * 这里可不可以是  抽象类？   接口类可能有一些方法是没有的， 类的其它方法能不能代理？？？？？？？？？？？？？？？？？？？
     */
    //Subject subject = new RealSubject();
    private Object object;

    public Proxy(Object object) {
        this.object = object;
    }



    /**
 * @author Firefly
 *  实现  InvocationHandler 的接口
 *
 */



    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before the display");
        Object result = method.invoke(object,args);
        System.out.println("after the display");
        System.out.println(result);
        return result;
    }


}
