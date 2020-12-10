package component;


public class SpiInterfaceImpl02 implements SpiInterface {

    private String otherName = "SpiInterfaceImpl02";  // 为了测试区分

    @Override
    public String get() {
        return otherName;
    }

}
