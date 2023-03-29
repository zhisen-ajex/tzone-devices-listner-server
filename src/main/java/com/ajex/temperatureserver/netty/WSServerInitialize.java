package com.ajex.temperatureserver.netty;

import com.ajex.temperatureserver.config.netty.WebSocketConfiguration;
import com.ajex.temperatureserver.netty.handler.HeartBeatHandler;
import com.ajex.temperatureserver.netty.handler.WSClientMsgHandler;
import com.ajex.temperatureserver.utils.SpringUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * Init Channel
 */
public class WSServerInitialize extends ChannelInitializer<SocketChannel> {
    private WSClientMsgHandler clientMsgHandler;
    private HeartBeatHandler heartBeatHandler;
    WSServerInitialize() {
        clientMsgHandler = SpringUtils.getBean(WSClientMsgHandler.class);
        heartBeatHandler = SpringUtils.getBean(HeartBeatHandler.class);
    }

    @Override
    protected void initChannel(SocketChannel ch) {

        WebSocketConfiguration webSocketConfiguration = SpringUtils.getBean(WebSocketConfiguration.class);

        ChannelPipeline pipeline = ch.pipeline();

        pipeline
/*            .addLast("decoder", new StringDecoder(CharsetUtil.UTF_8))
            .addLast("encoder", new StringEncoder(CharsetUtil.UTF_8))*/
            .addLast(new HttpServerCodec())
            //.addLast(new ChunkedWriteHandler())
            .addLast(new HttpObjectAggregator(1024 * 64))
                //.addLast(new IdleStateHandler(30, 30, 5, TimeUnit.SECONDS))
            .addLast(new IdleStateHandler(10, 10, 30, TimeUnit.MINUTES))
            .addLast(heartBeatHandler)
            .addLast(new WebSocketServerProtocolHandler(webSocketConfiguration.getContextPath()))
            .addLast(clientMsgHandler);
    }

}
