package basic.DesignPattern.creation.BuilderPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 16:33
 */

public class Test {

    public static void main(String[] args) {


        Director director = new Director();

        Product product1 = director.construct(new ConcreteBuilder1());

        Product product0 = director.construct(new ConcreteBuilder2());

        System.out.println(product0.getSize() + " " + product0.getLen());
        System.out.println(product1.getSize() + " " + product1.getLen());


    }
}
