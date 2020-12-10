package basic.DesignPattern.behavior.ObserverPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 16:32
 *
 *
 * jdk 已经自动实现了 观察者模式，  这是一个经常用到的 模式
 *
 * MVC   也就是用了这种模式， view 表示观察者  model 表示为  model   c是一个中间层
 *
 */


public class Client {

    public static void main(String[] args) {
        ConcreteObservable c = new ConcreteObservable();


        c.addObserver(new ConcreteObserverA());
        c.addObserver(new ConcreteObserverB());

        /**
         * @author Firefly
         * 要手动设置 有没有改变， 防止 有一些观察者接收不到消息
         */
        System.out.println(c.hasChanged());

        /**
         * @author Firefly
         * 告诉 观察目标  已经更改了 我提示的观察者！！！！
         */
        c.setChanged();
        System.out.println(c.hasChanged());

        c.notifyObservers("this is from Observable ");

    }
}
