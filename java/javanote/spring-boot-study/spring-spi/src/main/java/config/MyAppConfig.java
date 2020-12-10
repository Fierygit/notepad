package config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * @Author: liyang
 * @Date: 2020/11/4  16:51
 * @Description: 配置Spring 的组件扫描配包
 **/
@Configuration
@ComponentScan(value = {"config"  /*, "component" */})
public class MyAppConfig {
}
