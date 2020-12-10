package basic.thread;

import java.util.concurrent.CountDownLatch;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/3 19:21
 */

public class Latch {

    public void calc() throws Exception {
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(10);

        for (int i = 0; i < 10; i++) {
            new Thread(()  -> {
                try{
                    startLatch.await();
                    System.out.println(Thread.currentThread().getName() + ": i am here!!");
                    endLatch.countDown();
                }catch (Exception e){
                    System.out.println(e);
                }

            }).start();
        }
        long start, end;
        start = System.nanoTime();
        System.out.println("start count!!!");
        startLatch.countDown();
        endLatch.await();
        end = System.nanoTime();
        System.out.println(end - start);
    }

    public static void main(String[] args) throws Exception {
        Latch test = new Latch();
        test.calc();
    }

}
