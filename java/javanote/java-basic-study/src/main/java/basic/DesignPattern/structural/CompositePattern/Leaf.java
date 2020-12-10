package basic.DesignPattern.structural.CompositePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 11:30
 */

public class Leaf extends Component {


    private String str;

    public Leaf() {

    }

    public Leaf(String str) {
        this.str = str;
    }


    @Override
    public void display(int len) {
        System.out.println("Leaf " + this.str);
    }
}
