package Basic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author fireflychen
 */

@FunctionalInterface
interface FuncObj {

    void test();
}

/**
 * 1、 Consumer 消费调一个 2、 Function 函数，有参数有返回 3、 Predicate 谓语断言， 有参数，返回一个bool 4、 Supplier 提供者，
 *
 * 无参数，返回一个东西
 *
 * @author fireflychen
 */


public class TestLambda {

    int bar = 10;

    public void test(FuncObj foo) {
        foo.test();
    }

    public TestLambda callByLambda() {
        AtomicInteger tmp = new AtomicInteger(10);
        test(() -> {
            this.bar = 20;
            tmp.set(20);
            System.out.println("lambda can not change local variable");
        });
        return this;
    }

    public void callByInnerClass() {
        final int[] tmp = {123456};
        test(new FuncObj() {
            @Override
            public void test() {
                System.out.println("do not have this pointer" + tmp[0]);
            }
        });
    }

    public void call() {
        callByLambda().callByInnerClass();
    }

    public static void main(String[] args) {
        new TestLambda().call();
    }

}
