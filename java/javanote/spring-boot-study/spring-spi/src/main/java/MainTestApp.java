import component.Driver;
import component.SpiInterface;
import component.Tree;
import config.MyAppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
* @Author:  liyang
* @Date:    2020/11/4  16:48
* @Description:  测试入口
*
**/
public class MainTestApp {


    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyAppConfig.class);


        // 说白了, 我们面向接口编程, 而实现类是可以在外部去指定的, 这就是SPI
        SpiInterface spiInterface = context.getBean(SpiInterface.class);   // 从容器中获取 Bean 实例


        System.out.println(spiInterface.get());


        //
        Driver driver = context.getBean(Driver.class);
        System.out.println(driver.getName());


        Tree tree = context.getBean(Tree.class);
        System.out.println(tree);


        context.close();
    }
}
