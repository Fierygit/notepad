package basic.DesignPattern.behavior.ChainResponsibilityPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:14
 */

abstract class Handler {

    protected   Handler successor;

    public abstract void handleRequest(String str);

    public void setSuccessor(Handler handler){
        this.successor = handler;
    }

}
