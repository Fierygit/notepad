package basic.DesignPattern.structural.JavaDynamicProxy;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 12:23
 */


/**
 * @author Firefly
 *
 * spring 中用到了此类方法,  subject  相当于DAO
 *
 *                          realSubject  可以被代理，处理其他的DAO
 *
 */

public class RealSubject implements Subject{


    @Override
    public void display(String string) {
        System.out.println("this is real display!!" + "  "+ string);
    }



    public void test(){
        System.out.println("Test if the method can be proxied");
    }

}
