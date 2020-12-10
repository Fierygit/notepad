package basic.DesignPattern.structural.bridgePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/21 15:22
 */

public class PNGImage extends Image {
    @Override
    public void parseFile(String filename) {
        imageImp.doPaint(filename);
        System.out.print("PNG");
    }
}

