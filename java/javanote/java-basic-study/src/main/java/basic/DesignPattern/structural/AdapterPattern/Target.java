package basic.DesignPattern.structural.AdapterPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/21 19:09
 */

public abstract class Target {
    public void display(){
        System.out.println("this is the target abstract");
    }

    public abstract void adapter1();
    public abstract void adapter2();

}
