package basic.DesignPattern.behavior.CommandPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/15 17:06
 */

public class Client {


    public static void main(String[] args) {

        /**
         * @author Firefly
         * 把命令封装为 一个类， 通过调用不同的类来实现不同的命令
         *
         * 再内部  command 创建了 接受者
         */
        Command commandA = new ConcreteCommandA();
        Command commandB = new ConcreteCommandB();


        Invoker invoker = new Invoker(commandA);
        invoker.execute("invoker1");
        invoker.undo("invoker1");
        System.out.println("------------------");

        invoker.setCommand(commandB);
        invoker.execute("inveker2");
        invoker.undo("invoker2");

    }


}
