package basic.DesignPattern.structural.FlyweightPattern;


/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 10:20
 *
 */

public class Client {

    public static void main(String[] args) {
        Flyweight flyweight1, flyweight2;

        FlyweightFactory factory = new FlyweightFactory();

        Flyweight a1 = factory.getFlyweight("a");

        Flyweight b = factory.getFlyweight("b");

        Flyweight a2 = factory.getFlyweight("a");

        a1.display(new External("external"));
        b.display(new External("external"));

        System.out.println(a1 == a2);

    }


}
