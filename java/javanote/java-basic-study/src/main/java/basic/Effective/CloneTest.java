package basic.Effective;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/29 23:48
 */


public class CloneTest implements Cloneable {
    int a;

    @Override
    public Cloneable clone() throws CloneNotSupportedException {
        return (Cloneable) super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        CloneTest cloneTest = new CloneTest();
        System.out.println(cloneTest.clone() == cloneTest);
        System.out.println(cloneTest.clone().getClass() == cloneTest.getClass());
        System.out.println(cloneTest.clone().equals(cloneTest));
    }
}
