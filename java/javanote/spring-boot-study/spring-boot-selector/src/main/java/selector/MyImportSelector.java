package selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


/**
 *
 *  实现 ImportSelector 接口, 实现 selectImports() 方法 其返回值 String[]  数组是全限定类名
 *
 **/
public class MyImportSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {


        /**
         *  返回值 String[] 字符串数组中 每一个全限定类名所指向的类 都将会被实例化成Bean, 然后注册到 Spring 容器 ( 默认是,单例的)
         */
        return new String[]{ "components.Student",  "components.Tree"};

	}

}
