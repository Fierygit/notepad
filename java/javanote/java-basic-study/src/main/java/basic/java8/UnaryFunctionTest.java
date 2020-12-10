package basic.java8;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/28 9:55
 */

public class UnaryFunctionTest {

    private static final UnaryOperator<Object> IDENTITY_FN = (t) -> t + "\t";
    @SuppressWarnings("unchecked")
    public static <T> UnaryOperator<T> identityFunction() {
        return (UnaryOperator<T>) IDENTITY_FN;//返回一个父类， 转为子类
    }

    public static void main(String[] args) throws Exception {
        String[] strings = {"hello", "world"};
        for (String s : strings) System.out.print(identityFunction().apply(s));

        System.out.println();
        Number[] numbers = {1,2,3,4};
        for(Number n : numbers) System.out.print(identityFunction().apply(n));

        System.out.println();
        List<Integer> list = new ArrayList<>();
        list.add(1);
        Method add = list.getClass().getMethod("add", Object.class);
        add.invoke(list,"str");
        System.out.println(list);
        System.out.println(list.get(1));

    }

}
