package basic.jvm;

import java.util.ArrayList;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/22 9:43
 * -XX:PermSize=1B -XX:MaxPermSize=1B
 */

public class TestPerm {


    public static void main(String[] args) {
        ArrayList<String> strings = new ArrayList<>();
        int cnt = 0;
        while(true){
            // jdk  1.7 以后在常量池记录实例的引用， 因此测试不到错误
            strings.add(String.valueOf(cnt++).intern());
            System.out.println(cnt);
        }
    }


}
