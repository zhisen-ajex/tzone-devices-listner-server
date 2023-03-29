package com.ajex.temperatureserver.netty;

import com.ajex.temperatureserver.netty.handler.HttpClientMsgHandler;
import com.ajex.temperatureserver.utils.SpringUtils;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;

/**
 * Init Channel
 */
public class HttpServerInitialize extends ChannelInitializer<SocketChannel> {
    private HttpClientMsgHandler clientMsgHandler;
    HttpServerInitialize() {
        clientMsgHandler = SpringUtils.getBean(HttpClientMsgHandler.class);
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline
            .addLast(new HttpServerCodec())
            .addLast(new HttpObjectAggregator(1024 * 64))
            .addLast(new HttpServerExpectContinueHandler())
            .addLast(clientMsgHandler);
    }

}
