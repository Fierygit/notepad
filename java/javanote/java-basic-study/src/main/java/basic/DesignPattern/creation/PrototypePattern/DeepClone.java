package basic.DesignPattern.creation.PrototypePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 14:36
 */


/**
 * @author Firefly
 * 实现Cloneable接口， 直接调用Java的clone机制
 */
public class DeepClone implements Cloneable {
    private int easyType;
    private ValueType valueType;


    /**
     * @author Firefly
     * 每一个对象都clone， 实现深克隆
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        ValueType valueType= (ValueType) this.valueType.clone();
        DeepClone clone = (DeepClone)super.clone();
        clone.setValueType(valueType);
        return clone;
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
        DeepClone a = new DeepClone();
        a.setEasyType(1);
        a.setValueType(new ValueType("T"));
        a.setEasyType(2);
        DeepClone b = (DeepClone)a.clone();
        /**
         * @author Firefly
         * 克隆只能克隆 基本类型
         */
        System.out.println(a == b);
        System.out.println(b.getEasyType());
        System.out.println(b.getValueType() == a.getValueType());

    }

}
