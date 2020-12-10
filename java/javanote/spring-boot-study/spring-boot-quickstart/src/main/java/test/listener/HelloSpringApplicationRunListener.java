package test.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/11/28 11:31
 */

public class HelloSpringApplicationRunListener implements SpringApplicationRunListener {
    public HelloSpringApplicationRunListener() {
    }

    //必须有的构造器
    public HelloSpringApplicationRunListener(SpringApplication application, String[] args) {

    }

    Log log = LogFactory.getLog(this.getClass());

    @Override
    public void starting() {
        System.out.println("创建 SpringApplication");
        System.out.println("从当前类路径下找到META-INF/spring.factories配置的所有ApplicationContextInitializer；然后保存起来");
        System.out.println("从当前类路径下找到META-INF/spring.factories配置的所有ApplicationListener");
        System.out.println("创建 SpringApplication完毕, 运行 Run 方法");
        System.out.println("获取SpringApplicationRunListeners；从类路径下META-INF/spring.factories");
        System.out.println("SpringApplicationRunListener...starting.............................................");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        Object o = environment.getSystemProperties().get("os.name");
        log.warn("封装命令行参数 -> 准备环境 -> 创建环境完成");
        log.warn("SpringApplicationRunListener...environmentPrepared......................................" + o);
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.warn("准备上下文环境; 将environment保存到ioc中");
        log.warn("调用 applyInitializers(), 回调之前保存的所有的ApplicationContextInitializer的initialize方法");
        log.warn("SpringApplicationRunListener...contextPrepared......................................");
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.warn("调用  prepareContext()  方法");
        log.warn("SpringApplicationRunListener...contextLoaded.......................................");
    }

    @Override
    public void finished(ConfigurableApplicationContext context, Throwable exception) {
        log.warn("调用 refreshContext(context)");
        log.warn("刷新容器；ioc容器初始化（如果是web应用还会创建嵌入式的Tomcat)");
        log.warn("扫描，创建，加载所有组件的地方；（配置类，组件，自动配置）");
        log.warn("调用 afterRefresh(context, applicationArguments);");
        log.warn("从ioc容器中获取所有的ApplicationRunner和CommandLineRunner进行回调");
        log.warn("ApplicationRunner先回调，CommandLineRunner再回调");
        log.warn("SpringApplicationRunListener...finished..........................................");
    }
}
