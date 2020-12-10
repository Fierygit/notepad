package basic.Algorithms.Sort;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/30 14:57
 * <p>
 * <p>
 * 选择排序和插入排序都是， 只能移动相邻位置的数， 因此效率低下
 * <p>
 * shell 排序 让数的交换可以交换有间隔的数
 * <p>
 * 定义： 在一个数组中，每隔  h 个数， 组成的数组有序， 称为 h 有序数组 ！！！！important
 */

public class Shell {
    public static void sort(Comparable[] a) {
        int length = a.length;

        int h = 1;

        /**
         * @author Firefly
         * 初始的间隔设为大约1/3 长, 不整除3，
         */

        while (h < length/3) h = h * 3 + 1;

        while (h >= 1) {
            for (int i = h; i < length; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                            exch(a,j,j - h);
                }
            }
            System.out.print(h + " ");
            h /= 3;
        }
        System.out.println();

    }

    private static boolean less(Comparable a, Comparable b) {
        return Util.less(a, b);
    }

    private static void exch(Comparable[] a, int i, int j) {
        Util.exch(a, i, j);
    }

}
