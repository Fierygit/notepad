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

public interface Subject {
    public void display(String string);
}
