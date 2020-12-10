package component;


//@Component
//@Service
public class SpiInterfaceImpl implements SpiInterface {

    private String name = "SpiInterfaceImpl";  // 为了测试区分实现类

    @Override
    public String get() {
        return name;
    }


}
