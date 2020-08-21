package SourceStudy.Netty.IM;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/8 11:32
 */

public class ServerHandler extends SimpleChannelInboundHandler {

    // 全局的时间执行器， 是一个单例
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(simpleDateFormat.format(new Date()) + "\t" + "[client]" + channel.remoteAddress() + " 加入群聊！！！\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(simpleDateFormat.format(new Date()) + "\t" + "[client]" + channel.remoteAddress() + " 离开群聊！！！\n");
        System.out.println(simpleDateFormat.format(new Date()) + "\t" + "当前用户数： " + channelGroup.size());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date()) + "\t" + ctx.channel().remoteAddress() + " 上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(simpleDateFormat.format(new Date()) + "\t" + ctx.channel().remoteAddress() + " 下线了");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(simpleDateFormat.format(new Date()) + "\t"
                + ctx.channel().remoteAddress()
                + " say: " + msg);
        final Channel channel = ctx.channel();

        channelGroup.forEach(new Consumer<Channel>() {
            public void accept(Channel ch) {
                if (channel != ch) {
                    ch.writeAndFlush(simpleDateFormat.format(new Date()) + "\t" + "client: " + channel.remoteAddress() + " say: " + msg + "\n");
                } else {
                    ch.writeAndFlush(simpleDateFormat.format(new Date()) + "\t" + "自己： " + msg);
                }
            }
        });
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
