package basic.DesignPattern.structural.AdapterPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/21 19:13
 */

public class test {

    public static void main(String[] args) {
        Target adapter = new Adapter();

        adapter.adapter1();

        adapter.adapter2();
    }
}
