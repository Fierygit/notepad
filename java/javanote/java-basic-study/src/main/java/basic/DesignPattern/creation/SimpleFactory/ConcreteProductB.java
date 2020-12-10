package basic.DesignPattern.creation.SimpleFactory;

public class ConcreteProductB extends Product {

    public void printInfo() {
        System.out.println("I7-9673");
    }


    // 工厂模式可以不实现  抽象方法里面的函数， 空着
    public void donotImp() {
    }


}
