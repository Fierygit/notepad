package basic.DesignPattern.structural.AdapterPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/21 19:11
 */

public class Adapter extends Target {

    Adaptee1 adaptee1;
    Adaptee2 adaptee2;

    public Adapter() {
        this.adaptee1 = new Adaptee1();
        this.adaptee2 = new Adaptee2();
    }


    @Override
    public void adapter1() {
        adaptee1.interface1();
    }

    @Override
    public void adapter2() {
        adaptee2.interface1();
    }
}
