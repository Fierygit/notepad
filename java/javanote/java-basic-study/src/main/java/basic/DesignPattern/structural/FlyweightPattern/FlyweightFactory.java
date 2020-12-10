package basic.DesignPattern.structural.FlyweightPattern;

import java.util.HashMap;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 10:14
 */

public class FlyweightFactory {

    private HashMap flyweights = new HashMap();

    public Flyweight getFlyweight(String key){
        if(flyweights.containsKey(key)){
            return (Flyweight)flyweights.get(key);
        }else {
            Flyweight fw = new SharedConcreteFlyweight(key);
            flyweights.put(key,fw);
            return fw;
        }
    }

}
