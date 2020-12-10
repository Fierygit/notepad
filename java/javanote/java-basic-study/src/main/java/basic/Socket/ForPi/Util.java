package basic.Socket.ForPi;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/7 23:35
 */

public class Util {
    public static String toBinaryString(int num) {
        if (num == 0) return ""+0;
        String result = "";
        // 左面0的个数
        int n = Integer.numberOfLeadingZeros(num);
        System.out.println("num <<= n"+n + "  " +(num <<= n));
        for (int i=0; i<32-n; ++i) {
            int x = (Integer.numberOfLeadingZeros(num) == 0)?1:0;
            result += x;
            num <<= 1;
        }
        return result;
    }

}
