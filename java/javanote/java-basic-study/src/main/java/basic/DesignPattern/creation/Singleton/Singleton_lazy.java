package basic.DesignPattern.creation.Singleton;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 8:38
 */

public class Singleton_lazy {
    private static Singleton_lazy singleton = null;
    private  int  a = 1;

    private Singleton_lazy() {
        System.out.println("constructer");
    }

    public static Singleton_lazy getSingleton() {
        /**
         * 要用的时候再来初始化，所以称之为lazy
         */
        synchronized (Singleton_lazy.class) {
            if (singleton == null) {
                singleton = new Singleton_lazy();
            }
        }

        return singleton;
    }


    public int getA() {
        return singleton.a;
    }

    /**
     * 注意这里ser的值
     * @param a
     */
    public void setA(int a) {
        singleton.a = a;
    }

    public static void main(String[] args) {
        Singleton_lazy a = Singleton_lazy.getSingleton();
        System.out.println(a.getA());
        a.setA(2);
        System.out.println(a.getA());
        Singleton_lazy b = Singleton_lazy.getSingleton();
        System.out.println(b.getA());
        b.setA(3);
        System.out.println(b.getA());
    }

}
