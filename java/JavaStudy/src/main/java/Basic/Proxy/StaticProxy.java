package Basic.Proxy;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/6/8 9:50
 *
 * 用一个包装类包装， 重新生成静态calss 文件
 *
 */


public class StaticProxy implements Sell {
    private final Sell mVendor;

    public StaticProxy(Sell vendor) {
        this.mVendor = vendor;
    }

    public void sell() {
        System.out.println("before");
        mVendor.sell();
        System.out.println("after");
    }

    public void ad() {
        System.out.println("before");
        mVendor.ad();
        System.out.println("after");
    }

    public static void main(String[] args) {

    }
}

interface Sell {
    void sell();
    void ad();
}
