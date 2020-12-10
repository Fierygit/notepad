package basic.DesignPattern.creation.FactoryMethod;



/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/13 12:32
 *
 *
 *
 * 比简单的工厂模式多了一个， 抽象工厂类
 */

public class Client {

    public static void main(String[] args) {
        LoggerFactory loggerFactory = new DatabaseLoggerFactory();
        Logger logger = loggerFactory.createLogger();
        logger.writeLogger();

        LoggerFactory loggerFactory1 = new FileLoggerFactory();
        Logger logger1 = loggerFactory1.createLogger();
        logger1.writeLogger();


    }

}
