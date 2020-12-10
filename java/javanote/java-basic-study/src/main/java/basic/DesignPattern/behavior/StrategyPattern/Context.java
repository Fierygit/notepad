package basic.DesignPattern.behavior.StrategyPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:54
 */

public class Context {

    Strategy strategy;



    public  void display(){
        strategy.display();
    }


    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

}
