package basic.DesignPattern.structural.FlyweightPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/11/21 9:23
 */

public class External {
    String msg;

    public External() {
    }

    public External(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
