package basic.DesignPattern.structural.FlyweightPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 9:19
 */

 class SharedConcreteFlyweight extends Flyweight{

    String msg;

    public SharedConcreteFlyweight() {

    }

    public SharedConcreteFlyweight(String msg) {
        this.msg = msg;
    }


    @Override
    void display(External external) {
        System.out.println(this.msg);
        if(external != null)
        System.out.println("this is External " + external.getMsg());
    }
}
