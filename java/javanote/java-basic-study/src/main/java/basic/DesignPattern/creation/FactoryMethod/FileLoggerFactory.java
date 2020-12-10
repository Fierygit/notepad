package basic.DesignPattern.creation.FactoryMethod;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/13 12:25
 */

public class FileLoggerFactory implements  LoggerFactory{
    public Logger createLogger(){
       Logger logger = new FileLogger();
       return logger;
    }
}
