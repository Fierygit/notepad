package basic.java8.Lambda;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/22 21:50
 */


public class Test {

    public interface T {
        int a = 0;

        void test();
    }

    private T t;
    public int a;

    public Test(T t) {
        this.t = t;
        t.test();
    }


}

class TT {
    public static void main(String[] args) {
        new Test(() -> System.out.println("hello t1"));  // 中间有了不同的类,
        new Test(() -> System.out.println("hello t2"));

    }
}