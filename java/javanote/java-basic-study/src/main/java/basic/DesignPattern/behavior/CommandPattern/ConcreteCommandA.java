package basic.DesignPattern.behavior.CommandPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/15 16:49
 */

public class ConcreteCommandA extends Command {
    ReceiverA receiverA = null;
    ReceiverB receiverB = null;

    public ConcreteCommandA() {
        receiverA = new ReceiverA();
        receiverB = new ReceiverB();
    }

    @Override
    public void execute(String string) {
        receiverA.receive("commandAAA exe " + string);
        receiverB.receive("commandAAA exe " + string);
    }

    @Override
    public void undo(String string) {
        receiverA.receive("commandAAA undo" + string);
        receiverB.receive("commandAAA undo" + string);
    }
}
