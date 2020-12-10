package basic.DesignPattern.structural.DecoratorPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 15:52
 */

public class ConcreteComponent1 extends Component {

    public String str;

    public ConcreteComponent1() {
    }

    public ConcreteComponent1(String str) {
        this.str = str;
    }

    public void display() {
        System.out.println(str);
    }

}
