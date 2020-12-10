package basic.AOP;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/25 21:02
 */

// 表明这是一个切面类
@Aspect
public class Bar {


    @Before(value = "test")
    public void beforeCut() {
        System.out.println("hi i am before!!!");
    }


}
