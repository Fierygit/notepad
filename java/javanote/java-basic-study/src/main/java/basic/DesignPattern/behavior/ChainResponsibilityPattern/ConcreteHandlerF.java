package basic.DesignPattern.behavior.ChainResponsibilityPattern;

/**
 * @author Firefly
 * @version 1.0
 * @date 2019/12/11 17:17
 */

public class ConcreteHandlerF extends Handler{
    @Override
    public void handleRequest(String string) {
        if(string.contentEquals("")){
            System.out.println("null");
        }else if( string.charAt(0)  <= 'F' ){
            System.out.println("this is F!!  handler under the " + (int)'F');
        }else {
            System.out.println("noone can handler this !!");
        }

    }
}
