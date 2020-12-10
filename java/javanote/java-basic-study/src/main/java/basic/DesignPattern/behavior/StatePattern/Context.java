package basic.DesignPattern.behavior.StatePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/8 22:07
 */


/**
 * @author Firefly
 * 0A -> 1B  ->  2C -> 0A  模拟水的三个状态
 */

public class Context {

    /**
     * @author Firefly
     * 用一个state 来存储不同的状态， 并且拥有不同的表现
     */
    private State state;
    private int count;
    String name = "H2O";

    public Context() {
        count = 0;
        this.state = new ConcretteStateA(this);
    }


    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add() {
        this.state.add();
        this.state.display();
    }


    public void sub() {
        this.state.sub();
        this.state.display();
    }


}
