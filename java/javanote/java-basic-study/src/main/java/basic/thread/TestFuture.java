package basic.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/3 22:21
 */

public class TestFuture {
    private final FutureTask<String> future = new FutureTask<String>(new Callable<String>() {
        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            return "hello";
        }
    });

    private Thread thread = new Thread(future);

    public void start(){
        thread.start();
    }

    public String get(){
        try{
          return  future.get();
        }catch (Exception e){
            System.out.println(e);
            return "";
        }
    }

    public static void main(String[] args) {
        TestFuture testFuture = new TestFuture();
        testFuture.start();
        System.out.println("feture started!!!");
        System.out.println(testFuture.get());

    }

}
