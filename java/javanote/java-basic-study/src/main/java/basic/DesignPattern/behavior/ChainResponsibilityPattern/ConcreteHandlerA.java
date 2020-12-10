package basic.DesignPattern.behavior.ChainResponsibilityPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:17
 */

public class ConcreteHandlerA extends Handler{
    @Override
    public void handleRequest(String string) {
            if(string.contentEquals("")){
                System.out.println("null");
            }else if( string.charAt(0)  <= 'A' ){
                   System.out.println("this is A!!  handler under the " + (int)'A');
            }else {
                this.successor.handleRequest(string);
            }

    }
}
