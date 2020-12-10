package basic.Algorithms.Sort;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/30 14:13
 *
 *
 * 这个好比是 整理一堆纸牌， 现在有一堆混乱的牌，一堆整理好的牌
 * 不断从上到下从混乱的牌中取出牌， 然后插入到整理好的牌中！！
 *
 */


public class Insertion {

    public static void sort(Comparable[] a){

        for(int i = 0 ; i < a.length; i++){     //假设前面i个是排好的，后面的没排好
            for(int j = i; j >0 ; j-- ){         // 把j 移到合适的位置，
                if(Util.less(a[j], a[j-1])) Util.exch(a,j,j-1);
            }
        }
    }

}
