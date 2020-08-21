package RPC.netty;

import RPC.provider.HelloserviceImp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/9 0:15
 */

public class NettyServerHandler extends ChannelInboundHandlerAdapter {


    // 可以改成 tcp 直接流， 自己定义消息的格式， 调用不同的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg.toString().startsWith("**")) {
            System.out.println("receiveMSG = " + msg);
            ctx.pipeline().writeAndFlush(new HelloserviceImp().hello(msg.toString()
                    .substring(msg.toString().lastIndexOf("*") + 1)));
        }


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
