package basic.DesignPattern.structural.bridgePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/21 15:26
 */

public class test {

    public static void main(String[] args) {
        Image image = new JPGImage();

        ImageImp imageImp = new WindowsImp();
        image.setImageImp(imageImp);

        image.parseFile("hello");

    }
}
