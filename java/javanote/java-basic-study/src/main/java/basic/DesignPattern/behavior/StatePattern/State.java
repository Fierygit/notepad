package basic.DesignPattern.behavior.StatePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/8 22:01
 */

/**
 * @author Firefly
 *  A ->  B  -> C
 */

abstract class  State {

    Context context;
    public abstract void display();
    public abstract void add();
    public abstract void sub();
    public abstract void checkState();

}
