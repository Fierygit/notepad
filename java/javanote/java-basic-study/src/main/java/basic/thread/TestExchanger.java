package basic.thread;

import java.util.concurrent.Exchanger;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/3 21:45
 */

public class TestExchanger {
    final Exchanger<String> exchanger = new Exchanger<>();

    public void a() {
        System.out.println("a is running");
        String value = "helloa";
        try {

            Thread.sleep(2000);
            String temp = this.exchanger.exchange(value);
            System.out.print(Thread.currentThread().getName() + ":" + value + "   exch: ");
            System.out.println(temp);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void b() {
        System.out.println("b is running");
        String value = "hellob";
        try {
            String temp = this.exchanger.exchange(value);
            System.out.print(Thread.currentThread().getName() + ":" + value + "   exch: ");
            System.out.println(temp);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        TestExchanger te = new TestExchanger();
        new Thread(te::a).start();
        new Thread(te::b).start();
    }
}
