package basic.DesignPattern.structural.DecoratorPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 15:52
 */

public  class ComponentDecorator extends  Component{

    Component component;
    private String str;


    public ComponentDecorator(){}
    public ComponentDecorator(Component component){
        this.component = component;
    }

    public void display(){
        component.display();
    }

}
