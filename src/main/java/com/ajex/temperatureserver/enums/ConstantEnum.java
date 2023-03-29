package com.ajex.temperatureserver.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.io.Serializable;


@Getter
public enum ConstantEnum implements  Serializable {
    GENERIC_FIELD("msgtype"),
    SERVER_CONFIG_FIELD("@CMD,"),
    FILL_CMD_REQUEST_PARAM_FIELD("data"),
    /**
     * 0成功，1失败 （same as com.ajex.temperatureserver.dto.ResponseResult#sta）
     */
    FILL_MONGODB_HANDLER_RESULT_FIELD("status"),
    FILL_MONGODB_CREATE_TIME_FIELD("createTime");
    @JsonValue
    private String name;

    ConstantEnum(String name) {
        this.name = name;
    }

}
