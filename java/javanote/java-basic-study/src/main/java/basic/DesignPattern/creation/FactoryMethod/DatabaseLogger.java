package basic.DesignPattern.creation.FactoryMethod;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/13 12:29
 */

public class DatabaseLogger implements Logger{
    public void writeLogger(){
        System.out.println("databaseLogger");
    }

}
