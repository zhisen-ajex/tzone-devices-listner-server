/*
package com.ajex.temperatureserver.handler;

import com.ajex.temperatureserver.dto.ResponseResult;
import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.util.Collection;
import java.util.LinkedList;


@Slf4j
@Component
public class ClientMsgHandlers {

    private final Collection<ClientMsgHandler> clientMsgHandlers = new LinkedList<>();

    public ClientMsgHandlers(ApplicationContext applicationContext) {
        // Add all file handler
        addClientMsgHandlers(applicationContext.getBeansOfType(ClientMsgHandler.class).values());
    }

    public ResponseResult handle(ChannelHandlerContext ctx, TT18MsgTypeEnum typeEnum, JSONObject actualObj) {
        ResponseResult result = new ResponseResult();
        for (ClientMsgHandler clientMsgHandler : clientMsgHandlers) {
            result = clientMsgHandler.handle(ctx, typeEnum, actualObj);
        }
        return result;
    }


    */
/**
     * Adds client msg handlers.
     *
     * @param clientMsgHandlers client msg handler collection
     * @return current file client handlers
     *//*

    @NonNull
    public ClientMsgHandlers addClientMsgHandlers(@Nullable Collection<ClientMsgHandler> clientMsgHandlers) {
        if (!CollectionUtils.isEmpty(clientMsgHandlers)) {
            this.clientMsgHandlers.addAll(clientMsgHandlers);
        }
        return this;
    }
}
*/
