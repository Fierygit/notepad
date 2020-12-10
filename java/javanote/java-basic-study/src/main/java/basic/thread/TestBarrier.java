package basic.thread;


import java.util.concurrent.CyclicBarrier;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/3 20:08
 */


public class TestBarrier {

   private final CyclicBarrier cyclicBarrier;

   public TestBarrier(){
       this.cyclicBarrier = new CyclicBarrier(4, () ->{
           System.out.println("this is barrier!!! end");
       });
   }
    public void meeting(){
       for(int i = 0; i < 11; i++){
           try {
               new Thread(()->{
                   System.out.println(Thread.currentThread().getName() + ": i am here!!");
                   try{
                       this.cyclicBarrier.await();
                   }catch (Exception e){
                       System.out.println(e);
                   }
               }).start();

           } catch (Exception e) {
               e.printStackTrace();
           }
       }
    }
    public static void main(String[] args) {
        TestBarrier barrier = new TestBarrier();
        barrier.meeting();
    }
}
