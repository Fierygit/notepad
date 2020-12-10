package annotation;


import components.Dog;
import org.springframework.context.annotation.Import;
import selector.MyImportSelector;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)

@Import(value = {

        Dog.class,              // 直接 import 一个普通类, 可以被实例化bean,然后注册到IOC容器中

        MyImportSelector.class  // 根据  ImportSelector 接口的 selectImports()方法的 返回值 String[],
                                // 字符串数组中 每一个全限定类名所指向的类 都将会被实例化成Bean, 然后注册到 Spring 容器 ( 默认是,单例的)
})
public @interface MyEnableAutoImportBeans {
}
