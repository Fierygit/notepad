package basic.DesignPattern.creation.PrototypePattern;

import java.io.Serializable;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 10:54
 */

public class ValueType implements Cloneable, Serializable {
    String valueType;

    public ValueType() {
    }

    public ValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
