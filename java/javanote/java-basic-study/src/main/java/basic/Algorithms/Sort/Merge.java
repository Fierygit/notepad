package basic.Algorithms.Sort;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/30 15:56
 */

public class Merge {

    static Comparable[] b;

    public static void sort(Comparable[] a) {
        b = new Comparable[a.length];
        sort(a,0,a.length-1);
    }

    private static void sort(Comparable[] a, int left, int right) {
        if (right - left < 1) return;
        int mid = (right + left) / 2;



        sort(a, left, mid);                   //遍历  拆开 左边先
        sort(a, mid + 1, right);         //遍历  拆开  右边

        int l = left;
        int r = mid + 1;
        int index = left;


        /**
         * @author Firefly
         * merge 一下
         */
        while (l <= mid || r <= right) {
            if (  r > right  || l <= mid  && less(a[l], a[r])) b[index++] = a[l++];
            else if (less(a[r], a[l])) b[index++] = a[r++];
            else b[index++] = l <= mid ? a[l++] : a[r++];
        }

        for(int i = left; i <= right; i++) a[i] = b[i];



    }


    private static boolean less(Comparable a, Comparable b) {
        return Util.less(a, b);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Util.exch(a, i, j);
    }
}
