package config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @Author:  liyang
 * @Date:    2020/11/4  16:38
 * @Description:  定义了一个配置类, 该配置类 使用@Import注解导入 MyAutoConfigurationRegistrar
 *
 **/
@Configuration
@Import(MyAutoConfigurationRegistrar.class)  // MyAutoConfigurationRegistrar BD 注册器
public class MyAutoConfiguration {
}
