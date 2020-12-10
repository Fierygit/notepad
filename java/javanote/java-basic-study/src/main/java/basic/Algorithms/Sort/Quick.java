package basic.Algorithms.Sort;

import static basic.Algorithms.Sort.Test.getData;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/30 15:56
 * <p>
 * <p>
 * <p>
 * 每次选择一个数， 将比它小的数放到左边， 比它大的数放到 右边
 */


public class Quick {


    public static void sort(Comparable[] a) {
        sort(a, 0, a.length - 1);
    }


    private static void sort(Comparable[] a, int left, int right) {
        if (left >= right) return;

        int l = left;
        int r = right;
        int flag = right;
        /**
         * @author Firefly
         * 核心函数
         * 帮助记忆：
         * 选取 右边的第一个数作为标记  flag
         *
         *  left：           在left走边的数一定比flag 小
         *  right：          在right右边的数一定比flag da
         *  flag：           在 left 和 right中间
         *  left - flag      没有处理的数据
         *  flag - right     和flag数一样大的数据
         */
        while (left < flag) {
            if (less(a[right], a[flag])) exch(a, right--, flag--);
            else if (less(a[flag], a[left])) exch(a, left++, flag);
            else flag--;
        }

        sort(a, l, flag);
        sort(a, right, r);

    }

    public static void main(String[] args) {
        Integer[] a = getData(20);
        for(int i = 0; i < a.length; i++){
            System.out.print(a[i]);
            System.out.print(" ");
        }
        System.out.println();

        Quick.sort(a);
        for(int i = 0; i < a.length; i++){
            System.out.print(a[i]);
            System.out.print(" ");
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
