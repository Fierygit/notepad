package basic.DesignPattern.structural.bridgePattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/10/21 15:19
 */

public abstract class Image {
    protected  ImageImp imageImp;

    public void setImageImp(ImageImp imageImp){
        this.imageImp = imageImp;
    }

    public abstract  void parseFile(String filename);


}
