package basic.Algorithms.Sort;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/30 13:04
 *
 *
 * 选择排序， 这个比较简单， 就是冒泡排序！！！
 *
 *
 *
 *
 */

class Selection {


    public static void sort(Comparable[] a) {

        int length = a.length;
        int temp;
        for (int i = 0; i < length; i++) {
            temp = i;
            for (int j = i; j < length; j++) {
                temp = less(a[temp],a[j]) ? temp : j;
            }
            exch(a, i, temp);
        }
    }


    public static void exch(Comparable[] a, int i, int j) {
        Comparable temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    public static  boolean less(Comparable a, Comparable b){
        return a.compareTo(b) < 0;
    }




}
