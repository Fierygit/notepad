package Basic.Proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/6/8 10:22``
 */

public class CglibProxy {
    public static void main(String[] args) {
        DaoProxy daoProxy = new DaoProxy();

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Dao.class);
        enhancer.setCallback(daoProxy);

        Dao dao = (Dao) enhancer.create();
        dao.update();
        dao.select();
    }
}

class DaoProxy implements MethodInterceptor {

    /**
     * Object表示要进行增强的对象
     * Method表示拦截的方法
     * Object[]数组表示参数列表，基本数据类型需要传入其包装类型，如int-->Integer、long-Long、double-->Double
     * MethodProxy表示对方法的代理，invokeSuper方法表示对被代理对象方法的调用
     */
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("deal with: " + method.getName());
        if (method.getName().equals("update")) { // 如果是 update 方法， 不代理
            methodProxy.invokeSuper(o, objects);
            return o;
        }

        System.out.println("Before Method Invoke");
        methodProxy.invokeSuper(o, objects);
        System.out.println("After Method Invoke");
        return o;
    }
}

class Dao {

    //定义了 final 后不能继承， 因此不能被代理
    //final
    public void update() {
        System.out.println("PeopleDao.update()");
    }

    //final
    public void select() {
        System.out.println("PeopleDao.select()");
    }
}