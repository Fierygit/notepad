package basic.DesignPattern.creation.Singleton;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 8:33
 */

public class Singleton_hungry {
    /**
     * 在类里面构造自己， 而且是静态的， 因此称之为hungry，这里还可以直接用final
     */
    private static final Singleton_hungry instance = new Singleton_hungry();
    int a = 1;

    /**
     * 私有的构造方式，只有自己能够构造自己
     */
    private Singleton_hungry(){
        System.out.println("construct");
    }


    public static Singleton_hungry getInstance(){
        return instance;
    }




    public static void main(String[] args) {
        Singleton_hungry a = Singleton_hungry.getInstance();
        System.out.println(a.getA());
        a.setA(2);
        System.out.println(a.getA());
        Singleton_hungry b = Singleton_hungry.getInstance();
        System.out.println(b.getA());
        b.setA(3);
        System.out.println(b.getA());
    }

    public int getA() {
        return instance.a;
    }

    public void setA(int a) {
        instance.a = a;
    }


}
