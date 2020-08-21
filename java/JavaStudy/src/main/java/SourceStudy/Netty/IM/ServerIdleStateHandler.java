package SourceStudy.Netty.IM;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Firefly
 * @version 1.0
 * @date 2020/5/8 14:23
 */

public class ServerIdleStateHandler extends ChannelInboundHandlerAdapter {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case ALL_IDLE:
                    eventType = "all";
                case READER_IDLE:
                    eventType = "read";
                case WRITER_IDLE:
                    eventType = "write";
            }
            System.out.println(simpleDateFormat.format(new Date()) + "\t"
                    + ctx.channel().remoteAddress()
                    + " info: " + eventType);
          //  ctx.channel().close();
        }
    }
}
