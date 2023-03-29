package com.ajex.temperatureserver.handler;

import com.ajex.temperatureserver.dto.ResponseResult;
import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;


public interface ClientMsgHandler {

    TT18MsgTypeEnum getActionType();
    ResponseResult handle(ChannelHandlerContext ctx, TT18MsgTypeEnum typeEnum, JSONObject msg);

/*    boolean support(TT18MsgTypeEnum typeEnum);*/

}
