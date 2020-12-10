package basic.DesignPattern.structural.ProxyPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 11:46
 */

public class Proxy extends Subject{

    Subject subject = null;

    public Proxy() {
    }

    public Proxy(Subject subject) {
        this.subject = subject;
    }

    @Override
    void display() {

        System.out.println("before the real display!!");
        subject.display();
        System.out.println("after the real display!!");

    }
}
