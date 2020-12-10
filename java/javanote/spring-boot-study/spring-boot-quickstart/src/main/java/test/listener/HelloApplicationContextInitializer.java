package test.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/11/28 11:28
 */

public class HelloApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    Log log = LogFactory.getLog(this.getClass());


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
       log.warn("ApplicationContextInitializer...initialize..." + applicationContext);
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        beanFactory.registerSingleton("test",HelloSpringApplicationRunListener.class);
    }
}
