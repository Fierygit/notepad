package basic.Algorithms.Sort;


import java.util.Arrays;
import java.util.Date;
import java.util.Random;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/30 14:09
 */

/**
 * @author Firefly
 * java 会自动装箱， 所以我们自己实现的时候是，不能这样写的！！ Integer[] a = {1,2,3,5}
 * <p>
 * java 基本类型都是值传递， 因此这里使用  包装类来实现
 * <p>
 * Object 的传递是传引用  地址的值
 */
public class Test {
    public static void main(String[] args) {


        Integer[] a= getData(10000000);
        long start = new Date().getTime();
        Arrays.sort(a);
        long end = new Date().getTime();
        System.out.println(end - start);

        testall(1, 10000000);
        // Test();
    }

    public static void test() {

        Integer[] a = getData(20);

        Util.reverse(a);
        Selection.sort(a);
        System.out.println("selection sort:");
        Util.show(a);



        Util.reverse(a);
        System.out.println("insertion sort");
        Insertion.sort(a);
        Util.show(a);

        Util.reverse(a);
        Shell.sort(a);
        System.out.println("shell sort");
        Util.show(a);

        a = getData(6);
        Util.show(a);
        Quick.sort(a);
        System.out.println("quick sort");
        Util.show(a);

        a = getData(6);
        Util.show(a);
        Merge.sort(a);
        System.out.println("merge sort");
        Util.show(a);

    }


    public static void testall(int len, int size) {

        long[] time = new long[5];
        for (int i = 0; i < len; i++) {
            Integer[] temp = getData(size);
            time[0] += testTime("Merge", temp);

            time[1] += testTime("Quick", temp);
            time[2] += testTime("Shell", temp);
            time[3] += testTime("Insertion", temp);
            time[4] += testTime("Selection", temp);
        }

        System.out.println("Merge: " + time[0]);
        System.out.println("Quick: " + time[1]);
        System.out.println("Shell: " + time[2]);
        System.out.println("Insertion: " + time[3]);
        System.out.println("Selection: " + time[4]);


        //随机获取数据


    }

    private static long testTime(String name, Comparable[] temp) {


        long start = new Date().getTime();

        switch (name) {
            case "Merge":
                Merge.sort(temp);
                break;
            case "Insertion":
                Merge.sort(temp);
                break;
            case "Quick":
                Merge.sort(temp);
                break;
            case "Selection":
                Merge.sort(temp);
                break;
            case "Shell":
                Merge.sort(temp);
                break;
            default:
                break;
        }

        long end = new Date().getTime();
        return end - start;
    }


    public static Integer[] getData(int n) {
        Random random = new Random(new Date().getTime());
        Integer[] a = new Integer[n];
        for (int i = 0; i < n; i++) {
            a[i] = random.nextInt() % n;
        }
        return a;
    }

}
