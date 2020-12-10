package basic.DesignPattern.structural.FacadePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/13 20:05
 */

public class FacadeA extends AbstractFacade {
    private SubSystemA subSystemA = new SubSystemA();
    private SubSystemB subSystemB = new SubSystemB();
    private SubSystemC subSystemC = new SubSystemC();

    public FacadeA(){

    }

    @Override
    public void doSomething() {
        System.out.println("FacadeA!!!");
        subSystemA.methodA();
        subSystemB.methodB();
        subSystemC.methodC();
    }

}
