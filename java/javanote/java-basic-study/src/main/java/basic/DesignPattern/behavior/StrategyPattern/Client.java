package basic.DesignPattern.behavior.StrategyPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:59
 */

public class Client {




    public static void main(String[] args) {

        Context context = new Context();

        Strategy strategy1 = new ConcreteStrategyA();
        Strategy strategy2 = new ConcreteStrategyB();


        context.setStrategy(strategy1);
        context.display();

        context.setStrategy(strategy2);
        context.display();


    }

}
