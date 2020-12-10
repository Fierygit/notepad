package basic.DesignPattern.structural.DecoratorPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 15:52
 */

public class ConcreteComponent2 extends Component {

    public String str;

    public ConcreteComponent2() {
    }

    public ConcreteComponent2(String str) {
        this.str = str;
    }

    public void display() {
        System.out.println(str);
    }
}
