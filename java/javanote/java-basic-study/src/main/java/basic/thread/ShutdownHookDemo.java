package basic.thread;

import java.io.IOException;

public class ShutdownHookDemo {
    public void start(){
        System.out.println("demo");
        Runtime.getRuntime().addShutdownHook(new ShutDownHook());
    }

    public static void main(String[] args) throws IOException {
        new ShutdownHookDemo().start();
        System.in.read();
    }
}

class  ShutDownHook extends Thread{
    @Override
    public void run() {
        while (true) {
            System.out.println("sleep for a while");
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
