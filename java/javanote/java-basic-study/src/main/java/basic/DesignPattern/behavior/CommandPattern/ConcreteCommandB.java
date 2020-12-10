package basic.DesignPattern.behavior.CommandPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/15 16:49
 */

public class ConcreteCommandB extends Command {
    ReceiverA receiverA = null;
    ReceiverB receiverB = null;

    public ConcreteCommandB() {
         receiverA = new ReceiverA();
         receiverB = new ReceiverB();
    }

    @Override
    public void execute(String string) {
        receiverA.receive("commandBBB exe" + string);
        receiverB.receive("commandBBB exe" + string);
    }

    @Override
    public void undo(String string) {
        receiverA.receive("commandBBB undo" + string);
        receiverB.receive("commandBBB undo" + string);
    }
}
