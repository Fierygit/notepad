package basic.rhino;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.FileReader;
import java.net.URL;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/17 15:52
 */

public class Test {

    public static void main(String[] args) throws Exception {

        // 获取脚本引擎
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine engine = sem.getEngineByName("javascript");

        //定义变量，存储到引擎的上下文中
        engine.put("msg", "firefly1");
        String str = "var user = { name : 'Firefly', age : 19 };";
        str += "print(user.age);";

       //执行脚本
        engine.eval(str);
        System.out.println(engine.get("msg"));
        engine.eval("msg = 'firefly2'");
        System.out.println(engine.get("msg"));

        //定义函数
        engine.eval("function add(a,b){var sum = a + b; return sum;}");

        //取得接口
        Invocable jsInvoke = (Invocable) engine;

        //执行
        Object result = jsInvoke.invokeFunction("add",new Object[]{1,2} );
        System.out.println(result);


        /**
         * @author Firefly
         *  注意  classLoader 获取的路径是在src下， 无论是否有包存在
         */
        System.out.println(Test.class.getName());
        //执行一个js脚本
        URL url = Test.class.getClassLoader().getResource("basic/rhino/test.js");
        System.out.println(url);
        FileReader fileReader = new FileReader(url.getPath());
        engine.eval(fileReader);
        fileReader.close();
    }
}
