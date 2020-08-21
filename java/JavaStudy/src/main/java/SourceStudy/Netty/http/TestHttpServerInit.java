package SourceStudy.Netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/8 8:59
 */

public class TestHttpServerInit extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("TestServer",new HttpServerCodec());

        pipeline.addLast("handler",new TestHttpServerHandler());

        System.out.println(pipeline.getClass());
    }
}
