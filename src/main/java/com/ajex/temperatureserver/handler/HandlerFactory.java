package com.ajex.temperatureserver.handler;

import com.ajex.temperatureserver.enums.TT18MsgTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class HandlerFactory implements ApplicationContextAware {
    private final Map<TT18MsgTypeEnum,ClientMsgHandler> clientMsgHandlerMap = new HashMap<>();
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String,ClientMsgHandler> msgHandlerMap = applicationContext.getBeansOfType(ClientMsgHandler.class);
        msgHandlerMap.values().forEach(e -> clientMsgHandlerMap.putIfAbsent(e.getActionType(),e));
    }

    public ClientMsgHandler getClientHandler(TT18MsgTypeEnum typeEnum){
        return clientMsgHandlerMap.get(typeEnum);
    }
}
