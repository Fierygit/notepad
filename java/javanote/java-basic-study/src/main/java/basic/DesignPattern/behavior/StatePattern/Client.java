package basic.DesignPattern.behavior.StatePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/9 8:20
 */

public class Client {
    public static void main(String[] args) {

        Context context = new Context();
        System.out.println(context.name);

        context.add();
        context.add();
        context.add();


        context.sub();
        context.sub();
        context.sub();

    }
}
