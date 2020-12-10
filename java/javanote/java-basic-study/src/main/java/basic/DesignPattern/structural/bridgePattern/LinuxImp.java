package basic.DesignPattern.structural.bridgePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/21 15:16
 */

public class LinuxImp implements ImageImp {
    @Override
    public void doPaint(String type) {
        System.out.println("linux" + type);
    }
}
