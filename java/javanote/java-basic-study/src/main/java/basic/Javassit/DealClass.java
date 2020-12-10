package basic.Javassit;

import javassist.*;

import java.lang.reflect.Method;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/18 13:51
 */

public class DealClass {

    /**
     * @author Firefly
     * 获取  某一个类的数据
     */

    public void dealClass() throws Exception {

        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.get("Javassit.Emp");

        System.out.println(ctClass.getName());
        System.out.println(ctClass.getSimpleName());

        System.out.println(ctClass.getSuperclass());
        //System.out.println(ctClass.getInterfaces());
    }


    /**
     * @author Firefly
     * 给某一个类分配 新的方法
     */
    public void addMethods() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.get("Javassit.Emp");

        // 创建 方法
        CtMethod ctMethod = new CtMethod(CtClass.intType, "add",
                new CtClass[]{CtClass.intType, CtClass.intType}, ctClass);

        ctMethod.setModifiers(Modifier.PUBLIC);

        ctMethod.setBody("{System.out.println(\"this is add methods\");return $1 + $2;}");

        ctClass.addMethod(ctMethod);

        // 通过反射调用新的方法
        Class claszz = ctClass.toClass();

        Object object = claszz.newInstance();

        Method method = claszz.getDeclaredMethod("add", int.class, int.class);

        Object result = method.invoke(object, 200, 200);

        System.out.println(result);

    }


    /**
     * @author Firefly
     * 修改 某一个  已经顶一个方法
     */
    public void changeMethods() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        CtClass ctClass = pool.get("Javassit.Emp");

        // 创建 方法
        CtMethod ctMethod = ctClass.getDeclaredMethod("getName");
        if (ctClass.isFrozen()) {
            ctClass.defrost();
        }

        /**
         * @author Firefly
         * 类已经被加载了，所以不能更改了，
         */
        System.out.println(ctClass.getName());
      //  ctMethod.insertAfter("System.out.println(\"this is the change methods\");");
        // 通过反射调用新的方法
       // ctClass.addMethod(ctMethod);
        System.out.println(this.getClass().getClassLoader().getResource(""));
        Class claszz = this.getClass().getClassLoader().loadClass("Emp");

        Object object = claszz.newInstance();

        Method method1 = claszz.getDeclaredMethod("setName", int.class);
        method1.invoke(object, 10);

        Method method2 = claszz.getDeclaredMethod("getName");

        Object result = method2.invoke(object);

        System.out.println(result);

    }

}
