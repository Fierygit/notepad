package basic.DesignPattern.behavior.ChainResponsibilityPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:23
 *
 * 职责链，  只处理自己的职责， 如果自己没有权限， 将自己
 *
 */

public class Client {

    public static void main(String[] args) {
        Handler handler1 = new ConcreteHandlerA();
        Handler handler2 = new ConcreteHandlerD();
        Handler handler3 = new ConcreteHandlerF();
        handler1.setSuccessor(handler2);
        handler2.setSuccessor(handler3);

        handler1.handleRequest("ABC");
        handler1.handleRequest("D");
        handler1.handleRequest("F");
        handler1.handleRequest("G");
    }

}
