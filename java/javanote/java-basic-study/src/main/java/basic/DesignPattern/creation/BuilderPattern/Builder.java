package basic.DesignPattern.creation.BuilderPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 16:24
 */

public abstract class Builder {
    Product product = new Product();


    public abstract void buildSize();

    public abstract void buildLen();


    public Product getProduct(){
        return this.product;
    }



}
