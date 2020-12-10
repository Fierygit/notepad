package basic.Javassit;


/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/18 13:02
 */

public class Test {

    public static void main(String[] args) throws Exception {


        CreateClass createClass = new CreateClass();
        createClass.createClass();
        System.out.println("-------------------------");

        DealClass dealClass = new DealClass();
        dealClass.dealClass();

        System.out.println("----------------------------");


        dealClass.addMethods();

        System.out.println("----------------------------");

        dealClass.changeMethods();

    }

}