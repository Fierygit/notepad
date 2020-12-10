package basic.DesignPattern.behavior.CommandPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/15 16:50
 */

public class Invoker {

    private Command command = null;

    public Invoker(Command command) {
        this.command = command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void execute(String string) {
        command.execute(string);
    }

    public void undo(String string) {
        command.undo(string);
    }
}
