package basic.DesignPattern.behavior.MediatorPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/29 15:36
 */

public abstract class Component {

    Mediator mediator;


    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
        mediator.register(this);
    }



    public abstract void  dosomething();
    public abstract void  display();


}
