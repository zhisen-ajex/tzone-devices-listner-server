package com.ajex.temperatureserver.handler;

import com.ajex.temperatureserver.dto.DataResult;
import com.ajex.temperatureserver.dto.ResponseResult;
import com.ajex.temperatureserver.enums.CollectionEnum;
import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import com.ajex.temperatureserver.utils.DateUtils;
import com.ajex.temperatureserver.utils.MongoDBOps;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * client msg handler
 *
 */
@Slf4j
@Component
public class TimeServerHandler implements ClientMsgHandler {

    @Override
    public TT18MsgTypeEnum getActionType() {
        return TT18MsgTypeEnum.GET_SERVER_TIME;
    }

    @Override
    public ResponseResult handle(ChannelHandlerContext ctx, TT18MsgTypeEnum typeEnum, JSONObject msg) {
        /*if (!support(typeEnum)) {
            return ResponseResult.error("unknown type");
        }*/
        if (ObjectUtils.isEmpty(msg) ) {
            return ResponseResult.error("data is empty");
        }
        log.info("接收到消息类型为[{}]",typeEnum.getName());

        DataResult dataResult = new DataResult();
        dataResult.setServertime(DateUtils.timestampToDate(System.currentTimeMillis()));
        return ResponseResult.success(dataResult);
    }
/*
    @Override
    public boolean support(TT18MsgTypeEnum typeEnum) {
        return typeEnum.equals(TT18MsgTypeEnum.GET_SERVER_TIME);
    }*/
}
