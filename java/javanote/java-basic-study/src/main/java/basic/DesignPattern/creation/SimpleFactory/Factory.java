package basic.DesignPattern.creation.SimpleFactory;


/**
 * 工厂获取对象
 */
public  class Factory {

    public static Product getProduct(String arg){
        Product product = null;
        if(arg.equalsIgnoreCase("a")){
            product = new ConcreteProductA();
        }
        else if (arg.equalsIgnoreCase("b")){
            product = new ConcreteProductB();
        }
        return product;
    }
}
