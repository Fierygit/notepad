package basic.DesignPattern.behavior.CommandPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/15 16:48
 */

public abstract class Command {

    public abstract void execute(String string);
    public abstract void undo(String string);

}
