package basic.DesignPattern.creation.PrototypePattern;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/23 15:16
 *
 * 序列化 克隆是不是  深度克隆呢？？？？？ 有待解决！！！！！！
 */

public class SerializeClone {

    public static void main(String[] args) {

        ValueType valueType = new ValueType();

        valueType.setValueType("1");


        try {

            /**
             * @author Firefly
             * 先序列化为  字节流
             */
            ByteOutputStream bos = new ByteOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(valueType);
            byte [] b = bos.getBytes();

            /**
             * @author Firefly
             * 反序列化为 一个实体的对象， 此时已经另一个对象， 地址已经不相同了
             */
            ByteArrayInputStream bis = new ByteArrayInputStream(b);
            ObjectInputStream ois = new ObjectInputStream(bis);
            ValueType clone = (ValueType)ois.readObject();
            System.out.println(clone == valueType);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
