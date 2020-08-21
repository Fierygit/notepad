package Basic.Stream;

import java.util.*;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/6/7 22:50
 */

public class Test {
    public static void main(String[] args) {

        String arr1[] = new String[]{"as", "sdf", "dsf", "dfd", "sdf"};
        String arr2[] = {"as", "sdf", "dsf", "dfd", "sdf", "as"};
        List<String> test1 = new ArrayList<>(Arrays.asList(arr1));
        List<String> test2 = new ArrayList<>(Arrays.asList(arr2));

        System.out.println(test1.parallelStream().count());
        Hashtable<String, Boolean> hash = new Hashtable<>();


        test2.stream()
                .filter(str -> {
                    if (!hash.containsKey(str)) {
                        hash.put(str, true);
                        return true;
                    } else {
                        return false;
                    }
                })
                .forEach(str -> System.out.printf("%s\t\t", str));


    }
}
