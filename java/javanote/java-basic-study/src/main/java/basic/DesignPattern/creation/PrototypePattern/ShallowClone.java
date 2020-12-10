package basic.DesignPattern.creation.PrototypePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 14:36
 */

public class ShallowClone implements Cloneable {
    private int easyType;
    private ValueType valueType;


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getEasyType() {
        return easyType;
    }

    public void setEasyType(int easyType) {
        this.easyType = easyType;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        ShallowClone a = new ShallowClone();
        a.setEasyType(1);
        a.setValueType(new ValueType("T"));
        a.setEasyType(2);
        ShallowClone b = (ShallowClone)a.clone();
        /**
         * @author Firefly
         * 克隆只能克隆 基本类型
         */
        System.out.println(a == b);
        System.out.println(b.getEasyType());
        System.out.println(b.getValueType() == a.getValueType());

    }

}
