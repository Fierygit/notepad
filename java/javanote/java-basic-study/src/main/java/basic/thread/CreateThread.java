package basic.thread;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/3/22 10:47
 */

public class CreateThread {


    public static void main(String[] args) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("hello");
            }
        }).start();

    }

}
