package basic.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;


/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/3 20:27
 */

public class TestSemaphore {
    private final List<Integer> list;
    private final Semaphore sem;

    TestSemaphore(){
        this.list =new ArrayList<>();
        this.sem = new Semaphore(10, true);
    }

    public void addOne(){
        try {
            sem.acquire();
            list.add(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void removeOne(){
        list.remove(0);
        sem.release();
    }

    public static void main(String[] args) {
        TestSemaphore testSemaphore = new TestSemaphore();
        new Thread(()->{
            while(true){
                testSemaphore.addOne();
                System.out.println("addone cur size:" + testSemaphore.list.size());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(()->{
            while(true){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                testSemaphore.removeOne();
                System.out.println("removeone cur size:" + testSemaphore.list.size());
            }
        }).start();

    }

}
