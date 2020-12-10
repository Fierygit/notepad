package basic.DesignPattern.creation.BuilderPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 16:30
 */

public class Director {

    public Product construct(Builder cb) {
        cb.buildLen();
        cb.buildSize();
        return cb.getProduct();
    }

}
