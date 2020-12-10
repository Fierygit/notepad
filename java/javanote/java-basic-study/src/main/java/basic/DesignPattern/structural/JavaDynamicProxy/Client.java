//package Basic.DesignPattern.structural.JavaDynamicProxy;
//
//import java.lang.reflect.InvocationHandler;
//
///**
// * @author Firefly
// * @version 1.0
// * @date 2019/11/21 13:20
// */
//
//public class Client {
//
//    public static void main(String[] args) {
//
//
//        RealSubject realSubject = new RealSubject();
//        InvocationHandler handler = new Proxy(realSubject);
//        Subject proxy = (Subject)Proxy.newProxyInstance(Subject.class.getClassLoader(), new Class[]{Subject.class},handler);
//        /**
//         * @author Firefly
//         * 可以看出接口里面没有的方法，就不能代理
//         *
//         * 只可以有接口，不能有类，，，，，，，，，，，，，，，有局限性
//         *
//         */
//        proxy.display("hello");
//
//    }
//}
