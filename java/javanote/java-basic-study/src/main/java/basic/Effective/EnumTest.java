package basic.Effective;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/4/30 9:43
 */

public enum EnumTest {
    PLUS("+") {
        @Override
        public double apply(double x, double y) {
            return x + y;
        }
    }, MINUS("-") {
        @Override
        public double apply(double x, double y) {
            return x - y;
        }
    };

    private final String symbol;
    EnumTest(String s) {
        this.symbol = s;
    }
    @Override
    public String toString() {
        return this.symbol;
    }
    public abstract double apply(double x, double y);

    public static void main(String[] args) {
        double x = 1.3;
        double y = 1.4;
        for (EnumTest e : EnumTest.values()) {
            System.out.printf("%f %s %f = %f%n", x, e, y, e.apply(x, y));
        }
    }
}
