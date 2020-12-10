package basic.DesignPattern.creation.SimpleFactory;


public class Client {


    public static void main(String[] args) {
        Product product;
        product = Factory.getProduct("a");
        product.printInfo();


        product = Factory.getProduct("b");
        product.printInfo();

    }


}
