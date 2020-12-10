package basic.jvm;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/22 9:30
 */

public class Teststack {
    private int len = 1;
    public void test(){
        len++;
        test();
    }
    public static void main(String[] args) {
        Teststack t = new Teststack();
        t.test();
    }

}
