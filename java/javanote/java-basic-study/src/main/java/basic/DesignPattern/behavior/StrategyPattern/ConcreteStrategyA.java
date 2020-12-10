package basic.DesignPattern.behavior.StrategyPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:55
 */

class ConcreteStrategyA extends  Strategy{

    @Override
    public void display() {
        System.out.println(" this is A!!!");
    }
}
