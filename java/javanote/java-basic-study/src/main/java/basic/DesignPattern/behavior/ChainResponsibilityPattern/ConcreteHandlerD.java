package basic.DesignPattern.behavior.ChainResponsibilityPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:17
 */

public class ConcreteHandlerD extends Handler{
    @Override
    public void handleRequest(String string) {
        if(string.contentEquals("")){
            System.out.println("null");
        }else if( string.charAt(0)  <= 'D' ){
            System.out.println("this is D!!  handler under the " + (int)'D');
        }else {
            this.successor.handleRequest(string);
        }

    }
}
