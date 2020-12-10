package basic.DesignPattern.behavior.MediatorPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/29 15:46
 */

public class Client {

    /**
     * @author Firefly
     *
     * 当组件之间的关系成网状关系的时候，用一个 mediator 来处理之间的关系
     *
     * 类似  qq 群
     *
     *
     */

    public static void main(String[] args) {
        Mediator mediator = new ConcreateMediator();

        Component component1 = new ConcreateComponent1();
        Component component2 = new ConcreateComponent2();

        /**
         * @author Firefly
         * 使用mediator来处理关系
         */

        /**
         * 设置了mediator 后 ，自己也被这个mediator引用了
         */
        component1.setMediator(mediator);
        component2.setMediator(mediator);


        /**
         * @author Firefly
         * 下面的的方法回调 mediatpr 中的dosomething，  mediator会处理各个类之间的关系
         */

       component1.dosomething();
       component2.dosomething();



    }
}
