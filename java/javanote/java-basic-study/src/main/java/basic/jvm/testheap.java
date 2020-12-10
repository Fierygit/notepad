package basic.jvm;

import java.util.ArrayList;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/2/22 9:20
 * VM args: -Xms20m -Xmx20m
 */

public class testheap {
    static class test {
    }
    public static void main(String[] args) {
        ArrayList<test> tests = new ArrayList<>();
        while (true) {
            tests.add(new test());
        }
    }
}
