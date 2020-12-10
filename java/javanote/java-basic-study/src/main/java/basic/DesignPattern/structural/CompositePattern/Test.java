package basic.DesignPattern.structural.CompositePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 12:05
 */

public class Test {

    public static void main(String[] args) {

        Composite folder1 = new Composite("folder1");
        Composite folder2 = new Composite("folder2");
        Composite folder3 = new Composite("folder3");


        folder1.add(new Leaf("f1"));
        folder1.add(folder2);
        folder2.add(folder3);
        folder1.add(new Composite("folder22"));

        folder3.add(new Leaf("f3"));

        folder1.display(1);




    }
}
