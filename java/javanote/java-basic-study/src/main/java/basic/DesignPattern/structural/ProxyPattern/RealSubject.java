package basic.DesignPattern.structural.ProxyPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 11:48
 */

public class RealSubject  extends Subject{

    @Override
    void display() {
        System.out.println("this is real display!!!");
    }
}
