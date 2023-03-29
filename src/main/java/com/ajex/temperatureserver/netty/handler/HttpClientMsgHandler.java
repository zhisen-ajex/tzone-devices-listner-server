package com.ajex.temperatureserver.netty.handler;

import com.ajex.temperatureserver.dto.ResponseResult;
import com.ajex.temperatureserver.enums.CollectionEnum;
import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import com.ajex.temperatureserver.enums.ConstantEnum;
import com.ajex.temperatureserver.handler.HandlerFactory;
import com.ajex.temperatureserver.utils.MongoDBOps;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@ChannelHandler.Sharable
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HttpClientMsgHandler extends SimpleChannelInboundHandler<HttpObject> {

    /*private final ClientMsgHandlers clientMsgHandlers;*/
    private final HandlerFactory handlerFactory;
    private final MongoDBOps mongoDBOps;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) {
        if(msg instanceof HttpRequest){
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            log.info("HttpClientMsgHandler:{} client request!",ctx.channel().remoteAddress()  );
            String json = fullRequest.content().toString(CharsetUtil.UTF_8);
            if (Strings.isBlank(json)) {
                log.error("request content is empty");
                return ;
            }
            TT18MsgTypeEnum type = null;
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject = JSONObject.parseObject(json);
                type = TT18MsgTypeEnum.getByType(jsonObject.getString(ConstantEnum.GENERIC_FIELD.getName()));
            }catch (Exception e){
                if(!json.contains(ConstantEnum.SERVER_CONFIG_FIELD.getName())){
                    json = ConstantEnum.SERVER_CONFIG_FIELD.getName()+json;
                }
                log.info("request parse to object error:{}",JSONObject.toJSONString(e) );
                if(json.startsWith(ConstantEnum.SERVER_CONFIG_FIELD.getName())){
                    type = TT18MsgTypeEnum.SERVER_CONFIG;
                    jsonObject.put(ConstantEnum.FILL_CMD_REQUEST_PARAM_FIELD.getName(), json);
                }else{
                    log.error("request content error:{}",json );
                }
            }
            if (type == null){
                log.error("undefined data protocol");
                return;
            }
            ResponseResult result = handlerFactory.getClientHandler(type).handle(ctx, type, jsonObject);
            jsonObject.put(ConstantEnum.FILL_MONGODB_HANDLER_RESULT_FIELD.getName(),result.getSta());
            mongoDBOps.save(jsonObject, CollectionEnum.getByType(type.getType()).getName());
            /*ResponseResult result = clientMsgHandlers.handle(ctx, type, jsonObject);*/
            dealResponse(ctx,result);
        } else {
            log.error("HttpClientMsgHandler only handle HttpRequest");
        }
    }


    private void dealResponse(ChannelHandlerContext ctx,ResponseResult result){
        log.info("rs:{}",JSON.toJSONString(result));
        // 准备给客户端浏览器发送的数据
        ByteBuf byteBuf = Unpooled.copiedBuffer(JSON.toJSONString(result), CharsetUtil.UTF_8);
        // 设置 HTTP 版本, 和 HTTP 的状态码, 返回内容
        DefaultFullHttpResponse defaultFullHttpResponse =
                new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.OK, byteBuf);
        // 设置 HTTP 请求头
        // 设置内容类型是文本类型
        defaultFullHttpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        // 设置返回内容的长度
        defaultFullHttpResponse.headers().set(
                HttpHeaderNames.CONTENT_LENGTH,
                byteBuf.readableBytes());
        // 写出 HTTP 数据
        ChannelFuture cf = ctx.writeAndFlush(defaultFullHttpResponse);
        if (!cf.isSuccess()) {
            log.error("HttpClientMsgHandler Response:{} failed ",ctx.channel().remoteAddress());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("HttpClientMsgHandler exceptionCaught:{}",JSON.toJSONString(cause));
        ctx.close();
    }


}
