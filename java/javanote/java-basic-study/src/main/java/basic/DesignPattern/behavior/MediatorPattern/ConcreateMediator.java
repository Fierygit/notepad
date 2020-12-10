package basic.DesignPattern.behavior.MediatorPattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/29 15:35
 */

public class ConcreateMediator implements Mediator {

    List<Component> components = new ArrayList<>();


    @Override
    public void register(Component component) {
        components.add(component);
    }

    @Override
    public void dosomething(int code) {
        if (code == 1) {

            Component component1 = components.get(0);
            Component component2 = components.get(1);

            System.out.println("component1 first!!!");
            component1.display();
            component2.display();

        } else if (code == 2) {

            Component component1 = components.get(0);
            Component component2 = components.get(1);

            System.out.println("component2 first!!!");

            component2.display();
            component1.display();
        }

    }
}
