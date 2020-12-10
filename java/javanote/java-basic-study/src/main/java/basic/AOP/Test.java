package basic.AOP;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/25 21:41
 */

public class Test {

    static void test() throws Throwable {
        // 模拟容器, 解析类的注释， 看有没有aspect 注释， 有的话说明是一个切面类
        Class<?> bar = Class.forName("basic.AOP.Bar");

        // 模拟容器， 加载一个  Foo
        Class<?> foo = Class.forName("basic.AOP.Foo");

        // 代理容器创建的一个 实例对象
        Proxy proxy = new Proxy(bar);
        proxy.invoke(foo.newInstance(),foo.getMethod("foo"),null);

    }

    public static void main(String[] args) throws Throwable {
        test();
    }
}
