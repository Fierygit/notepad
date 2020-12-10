package basic.jvm;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/14 10:44
 */

public  class TestStaticDispatcher {

    static abstract class  Human{}
    static class Man extends Human{}
    static class Woman extends Human{}
    static void  sayHello(Human human){System.out.println("hey guy");}
    static void  sayHello(Man human){System.out.println("hey man");}
    static void  sayHello(Woman human){System.out.println("hey human");}

    public static void main(String[] args) {
        Human man = new Man();
        Human woman = new Man();
        sayHello(man);
        sayHello(woman);
    }
}
