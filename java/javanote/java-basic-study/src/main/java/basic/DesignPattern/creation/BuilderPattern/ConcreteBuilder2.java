package basic.DesignPattern.creation.BuilderPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 16:28
 */

public class ConcreteBuilder2 extends Builder {

    @Override
    public void buildSize() {
        this.product.setSize("small");
    }

    @Override
    public void buildLen() {
        this.product.setLen("10");
    }


}
