package basic.Algorithms.Sort;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/30 14:04
 */

public class Util {

    public static void show(Comparable[] a){
        for (Comparable i : a) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    /**
     * @author Firefly
     * 头尾交换
     */
    public static  void reverse(Comparable[] a){
        for (int i = 0; i < ( (a.length - 1) / 2); i++){
            exch(a,i, a.length - i - 1);
        }
    }

    public static  boolean less(Comparable a, Comparable b){
        return a.compareTo(b) < 0;
    }

}
