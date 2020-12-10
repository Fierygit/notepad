package basic.DesignPattern.structural.FacadePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/13 20:05
 */

public class FacadeB extends AbstractFacade {
    private SubSystemA subSystemA = new SubSystemA();
    private SubSystemB subSystemB = new SubSystemB();
    private SubSystemC subSystemC = new SubSystemC();

    public  FacadeB(){

    }

    @Override
    public void doSomething() {
        System.out.println("FacadeB!!!");
        subSystemA.methodA();
        subSystemB.methodB();
        subSystemC.methodC();
    }

}
