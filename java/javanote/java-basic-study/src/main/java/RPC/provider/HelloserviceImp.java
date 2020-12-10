package RPC.provider;

import RPC.pub.HelloService;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/9 0:10
 */

public class HelloserviceImp implements HelloService {

    @Override
    public String hello(String msg) {
        System.out.println(msg);
        return "hello";
    }
}
