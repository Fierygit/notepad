import config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 测试类
 **/

public class TestImportSelector {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		System.out.println("IOC容器初始化完成。。。。。");


		// 根据class类型, 去IOC容器获取 Bean
//		Dog dog = context.getBean(Dog.class);
//		Tree tree = context.getBean(Tree.class);
//        Student student = context.getBean(Student.class);
//
//
//        System.out.println(dog);
//        System.out.println(tree);
//        System.out.println(student);



        //根据beanName, 去IOC容器获取 Bean
		Object dog2 = context.getBean("components.Dog");
		Object tree2 = context.getBean("components.Tree");
        Object student2 = context.getBean("components.Student");

        System.out.println(dog2);
        System.out.println(tree2);
        System.out.println(student2);


	}

}
