package basic.java8.Lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/23 15:57
 */

@FunctionalInterface
interface Converter<F, T> {
    int a = 0;

    T converte(F f);
}



class FunctionalInterfaceTest {
    public static void main(String[] args) {
        Converter<String, Integer> converter = Integer::valueOf;
        Integer converte = converter.converte("1234");
        System.out.println(converte);
        List<Integer> list = new ArrayList<>();
    }

}
