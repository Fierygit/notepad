package basic.DesignPattern.structural.DecoratorPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 15:53
 */

public class ConcreteDecorator extends ComponentDecorator {

    public ConcreteDecorator(){

    }

    public ConcreteDecorator(Component component) {
        super(component);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public void display() {
        super.display();
        add("decorator");

    }

    private void add(String str){
        System.out.println(str);
    }
}
