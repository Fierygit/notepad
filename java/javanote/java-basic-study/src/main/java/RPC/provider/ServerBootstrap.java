package RPC.provider;

import RPC.netty.NettyServer;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/9 0:11
 */

public class ServerBootstrap {
    public static void main(String[] args) {
        NettyServer.startServer("localhost", 8888);
    }
}
