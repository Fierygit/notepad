package basic.DesignPattern.structural.CompositePattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 11:58
 */

public class Composite extends Component {
    private List<Component> components = new ArrayList<>();
    private String str;

    public Composite() {
    }

    public Composite(String str) {
        this.str = str;
    }

    public List<Component> getLeafs() {
        return components;
    }

    public Component getLeaf(int index) {
        return components.get(index);
    }

    public void add(Component component) {
        this.components.add(component);
    }

    public void remove(Component component) {
        components.remove(component);
    }


    public void display(int len) {
        System.out.println("Composite " + str );
        for (Component c : this.components) {
            int temp = len;
            while (temp-- > 0) System.out.print("   ");
            c.display(len+1);
        }
    }


}
