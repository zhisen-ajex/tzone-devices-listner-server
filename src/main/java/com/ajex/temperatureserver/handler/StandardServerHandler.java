package com.ajex.temperatureserver.handler;

import com.ajex.temperatureserver.dto.ResponseResult;
import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * client msg handler
 *
 */
@Slf4j
@Component
public class StandardServerHandler implements ClientMsgHandler {


    @Override
    public TT18MsgTypeEnum getActionType() {
        return TT18MsgTypeEnum.STANDARD_DATA;
    }

    @Override
    public ResponseResult handle(ChannelHandlerContext ctx, TT18MsgTypeEnum typeEnum, JSONObject msg) {
        if (ObjectUtils.isEmpty(msg) ) {
            return ResponseResult.error("data is empty");
        }
        log.info("接收到消息类型为[{}]", typeEnum.getName());
        return ResponseResult.success();
    }
}
