package com.ajex.temperatureserver.handler;

import com.ajex.temperatureserver.dto.DataResult;
import com.ajex.temperatureserver.dto.ResponseResult;
import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import com.ajex.temperatureserver.enums.ConstantEnum;
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
public class ServerConfigHandler implements ClientMsgHandler {


    @Override
    public TT18MsgTypeEnum getActionType() {
        return TT18MsgTypeEnum.SERVER_CONFIG;
    }

    @Override
    public ResponseResult handle(ChannelHandlerContext ctx, TT18MsgTypeEnum typeEnum, JSONObject msg) {
        if (ObjectUtils.isEmpty(msg) ) {
            return ResponseResult.error("data is empty");
        }
        log.info("接收到消息类型为[{}]", typeEnum.getName());
        String originData = msg.getString(ConstantEnum.FILL_CMD_REQUEST_PARAM_FIELD.getName());
        int first = originData.indexOf(",");
        int last = originData.lastIndexOf(",");
        String substring = originData.substring(first+1,last);
        DataResult dataResult = new DataResult();
        dataResult.setDowncmd(substring);
        return ResponseResult.success(dataResult);
    }
}
