package config;

import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.core.type.AnnotationMetadata;

import java.util.List;


/**
 * @Author:  liyang
 * @Date:    2020/11/4  16:38
 * @Description:
 *
 **/
public class MyAutoConfigurationRegistrar implements ImportBeanDefinitionRegistrar, BeanClassLoaderAware {

    @Autowired
	private ClassLoader beanClassLoader; //类加载器

    /**
     *
     * BeanClassLoaderAware 接口起作用, 注入 ClassLoader, 这里获取到的是: AppClassLoader
     *
     */
	@Override
	public void setBeanClassLoader(ClassLoader classLoader) {
		this.beanClassLoader = classLoader;
	}


	/*

	META-INF/spring.factories  文件中

	key -> MyAutoConfiguration
	values -> [ com.liy.teaching.spi.component.SpiInterfaceImpl02,  com.liy.teaching.spi.component.Driver ]

	String[] <-> values

	 */

	/**
	* @Author:  liyang
	* @Date:    2020/11/4  17:09
	* @Description:  使用 BeanDefinitionRegistry 注册 BD
	*
	**/
	@Override    // 如果大家不理解 或者不知道BeanDefinitionRegistry, 就是直接理解成 BeanDefinitionRegistry, 可以向IOC容器中注册Bean
	public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {


        List<String> classStringList = SpringFactoriesLoader.loadFactoryNames(MyAutoConfiguration.class, beanClassLoader);

        // META-INF/spring.factories
        // key -> [ aa, bb, cc ]

        System.out.println( "loadFactoryNames :  " + classStringList );

        // 如果 spring.factories 里为空
        if (classStringList.isEmpty()) {
            System.out.println("META-INF/spring.factories is null");
            return;
        }

        //
        for (String classFullName : classStringList) {
            System.out.println( " ------- META-INF/spring.factories   获取到的 classFullName ---->  " + classFullName );
            try {
                Class<?> clazz = beanClassLoader.loadClass(classFullName);
                String simpleName = clazz.getSimpleName();
                // 如果已经存在该 classFullName  的 BD
                if (registry.containsBeanDefinition(classFullName)) {
                    continue;
                }

                // 无参构造创建BD
                BeanDefinition bd = BeanDefinitionBuilder.rootBeanDefinition(classFullName).getBeanDefinition();
                registry.registerBeanDefinition( simpleName,  bd);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
	}

}
