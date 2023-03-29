package com.ajex.temperatureserver.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Heart Beat Handler. If the client's heartbeat frames is not received for a long time

 */

@ChannelHandler.Sharable
@Slf4j
@Component
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    private static final ByteBuf HEARTBEAT_SEQUENCE =
            Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
                    "HEARTBEAT", CharsetUtil.UTF_8));

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        log.info("coming HeartBeatHandler userEventTriggered");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            ctx.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                    .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            if (event.state() == IdleState.READER_IDLE) {
                log.info("READER_IDLE...");
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("WRITER_IDLE...");
            } else if (event.state() == IdleState.ALL_IDLE) {
                // Closes the channel which state is ALL_IDLE
                Channel channel = ctx.channel();
                channel.close();
                log.info("close channel: {}", channel.id().asLongText());
            }


        }else{
            super.userEventTriggered(ctx,evt);
        }

    }

}
