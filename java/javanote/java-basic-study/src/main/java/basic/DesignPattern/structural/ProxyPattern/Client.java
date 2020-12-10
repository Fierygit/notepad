package basic.DesignPattern.structural.ProxyPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 11:59
 */

public class Client {

    public static void main(String[] args) {

        Subject subject = new Proxy(new RealSubject());
        subject.display();
    }
}
