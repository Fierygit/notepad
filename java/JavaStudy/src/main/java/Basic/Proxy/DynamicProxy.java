package Basic.Proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/6/8 9:53
 */


class Vender implements Sell {
    public void sell() {
        System.out.println("In sell method");
    }

    public void ad() {
        System.out.println("ad method");
    }
}

class DynamicProxy implements InvocationHandler {
    //obj为委托类对象;
    private Object obj;

    public DynamicProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("before");
        Object result = method.invoke(obj, args);
        System.out.println("after");
        return result;
    }

    public static void main(String[] args) {
        InvocationHandler inter = new DynamicProxy(new Vender());
        Sell sell = (Sell) (Proxy.newProxyInstance(Sell.class.getClassLoader(), new Class[]{Sell.class}, inter));
        sell.ad();
    }
}


