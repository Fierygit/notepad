package RPC.comsumer;

import RPC.netty.NettyClient;
import RPC.pub.HelloService;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/9 8:41
 */

public class ClientBootstrap {

    public static final String providerName = "**";

    public static void main(String[] args) {

        NettyClient client = new NettyClient();

        HelloService service = (HelloService) client.getBean(HelloService.class, providerName);

        for (int i = 0; i < 10; i++) {
            System.out.println(service.hello("hi"));
            // call的那个方法加了锁， 因此客户端只有一个线程能够唤醒，保证了线程安全

        }


    }

}
