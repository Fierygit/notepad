package basic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CreateThreadByCallable implements Callable<Integer> {
    public static void main(String[] args) {
        CreateThreadByCallable ctt = new CreateThreadByCallable();
        FutureTask<Integer> ft = new FutureTask<>(ctt);
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "A" + i);
            if (i == 20) {
                new Thread(ft, "B").start();
            }
        }
        try {
            System.out.println("B" + ft.get());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Override
    public Integer call() throws Exception {
        int i = 0;
        for (; i < 100; i++) {
            //System.out.println(Thread.currentThread().getName() + " " + i);
        }
        return i;
    }
}