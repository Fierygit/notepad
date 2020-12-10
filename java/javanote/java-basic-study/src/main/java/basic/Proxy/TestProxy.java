package basic.Proxy;

import java.lang.reflect.Proxy;

interface HelloService {
    String hello(String msg);
}

class TestProxy {
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> providerName + args[0]);
    }
    // 代理接口相当于实现了 hello 方法， () --> (providerName + args[0])
    // 此时可以通过将参数传输给远端服务器， 然后将返回结果返回回来, 方法的执行过程在远端
    public static void main(String[] args) {
        HelloService bean = (HelloService) new TestProxy()
                .getBean(HelloService.class, "this is return by proxy");
        System.out.println(bean.hello(" this is param"));
    }
}