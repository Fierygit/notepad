package basic.DesignPattern.behavior.ObserverPattern;

import java.util.Observable;
import java.util.Observer;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 16:26
 */

public class ConcreteObserverA implements Observer {

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("this is A!!" + arg);
    }
}
