package basic.DesignPattern.creation.PrototypePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 10:00
 */

public class ConcretePrototype extends Prototype {

    private ValueType valueType;
    private int easyType;


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


    public ConcretePrototype Clone() {
        /**
         * question
         *
         *
         */
        ConcretePrototype prototype= new ConcretePrototype();
        prototype.setEasyType(this.easyType);
        prototype.setValueType(this.valueType);
        return prototype;
    }

    public static void main(String[] args) {

        ConcretePrototype prototype1 = new ConcretePrototype();

        prototype1.setValueType(new ValueType());
        prototype1.setEasyType(1);

        ConcretePrototype prototype2 = prototype1.Clone();

        System.out.println(prototype1 == prototype2);
    }
}
