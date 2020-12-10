package basic.DesignPattern.structural.CompositePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/26 11:29
 */

public abstract class Component {

    /**
     *采用半透明的方式实现， 叶子函数不能够调用，getLeaf等函数
     */
    public abstract void display(int len);

}
