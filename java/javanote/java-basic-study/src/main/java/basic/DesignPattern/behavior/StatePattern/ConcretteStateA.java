package basic.DesignPattern.behavior.StatePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/8 22:03
 */

public class ConcretteStateA extends State {

    public ConcretteStateA(State state) {
        this.context = state.context;
    }

    public ConcretteStateA(Context context) {
        this.context = context;
    }

    @Override
    public void display() {
        System.out.println("this is A!!!");
    }

    @Override
    public void add() {
        context.setCount(1);
        checkState();
    }

    @Override
    public void sub() {
        context.setCount(2);
        checkState();
    }

    @Override
    public void checkState() {
        if (this.context.getCount() == 0)
            this.context.setState(new ConcretteStateA(this));
        else if (this.context.getCount() == 1)
            this.context.setState(new ConcretteStateB(this));
        else if (this.context.getCount() == 2)
            this.context.setState(new ConcretteStateC(this));
    }
}
