package basic.DesignPattern.structural.FacadePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/13 20:10
 *
 *
 *
 * 给不同的系统    一个统一的入口
 */

public class Client {

    public static void main(String[] args) {

        AbstractFacade test;

        test = new FacadeA();
        test.doSomething();

        test = new FacadeB();
        test.doSomething();

    }


}
