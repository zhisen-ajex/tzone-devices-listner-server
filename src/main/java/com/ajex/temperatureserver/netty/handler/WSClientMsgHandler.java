package com.ajex.temperatureserver.netty.handler;

import com.ajex.temperatureserver.dto.ResponseResult;
import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import com.ajex.temperatureserver.enums.ConstantEnum;
import com.ajex.temperatureserver.handler.HandlerFactory;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@ChannelHandler.Sharable
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class WSClientMsgHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    /*private final ClientMsgHandlers clientMsgHandlers;*/
    private final HandlerFactory handlerFactory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        log.info("channelRead0:{}",msg.text());
        String json = msg.text();
        JSONObject jsonObject = JSONObject.parseObject(json);
        TT18MsgTypeEnum type = TT18MsgTypeEnum.getByType(jsonObject.getString(ConstantEnum.GENERIC_FIELD.getName()));
        ResponseResult result = handlerFactory.getClientHandler(type).handle(ctx, type, jsonObject);
        //ResponseResult result = clientMsgHandlers.handle(ctx, type, jsonObject);
        log.info("rs:{}",JSON.toJSONString(result));
        TextWebSocketFrame textWebSocketFrame = new TextWebSocketFrame(JSON.toJSONString(result));
        ChannelFuture cf = ctx.writeAndFlush(textWebSocketFrame);
        if (!cf.isSuccess()) {
            log.error("Send failed: " + cf.cause());
        }
    }

/*
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将队列中的消息写入到SocketChannel中发送给对方
        *//**
         * 从性能角度考虑，为了防止频繁地唤醒Selector进行消息发送，Netty的write方法并不直接将消息写入SocketChannel中，
         * 调用write方法只是把待发送的消息放到缓冲数组中，再通过调用flush方法，将发送缓冲区中的消息全部写到SocketChannel中。
         *//*
        log.info("channelReadComplete:{}",JSON.toJSONString(ctx));
        //ctx.flush();*/




    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("ClientMsgHandler exceptionCaught:{}",JSON.toJSONString(cause));
        //当发生异常，关闭ChannelHandlerContext，释放和ChannelHandlerContext相关联的句柄等资源。
        ctx.close();
    }


}
