package basic.Javassit;

import javassist.*;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/18 13:49
 */

public class CreateClass {

    public void createClass() throws Exception {
        ClassPool pool = ClassPool.getDefault();

        CtClass cc = pool.makeClass("Javassit.Emp1");

        //添加属性
        CtField ctField1 = CtField.make("private int num;", cc);
        CtField ctField2 = CtField.make("private String name;", cc);

        cc.addField(ctField1);
        cc.addField(ctField2);


        //! 添加放方法

        CtMethod m1 = CtMethod.make("public int getName(){return name;}", cc);
        CtMethod m2 = CtMethod.make("public void setName(){this.name = name;}",cc);

        cc.addMethod(m1);
        cc.addMethod(m2);

        //添加constructor
        CtConstructor ctConstructor = new CtConstructor(new CtClass[]{CtClass.intType, pool.get("java.lang.String")}, cc);
        ctConstructor.setBody("{this.name = name; this.num = num;}");
        cc.addConstructor(ctConstructor);

        String path = Test.class.getClassLoader().getResource("").getPath();
        System.out.println(path);
        cc.writeFile(path);
    }

}
