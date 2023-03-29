package com.ajex.temperatureserver.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Arrays;


@Getter
public enum TT18MsgTypeEnum implements  Serializable {
    GET_SERVER_TIME("1", "获取服务器时间"),
    HEART_BEAT("2", "心跳包"),
    STANDARD_DATA("3", "标准数据协议"),
    SERVER_CONFIG("@CMD,", "服务器下行设置"),
    MACHINE_REPLY_AFTER_RECEIVE_SERVER_CONFIG("4", "机器收到下行指令后回复"),
    MACHINE_VERSION("5", "机器版本信息"),
    REPLY_TAG_CONFIG("6","回复tag设置或者读取信息(预留)");

    @JsonValue
    private String type;

    private String name;

    TT18MsgTypeEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public String getValue() {
        return this.type;
    }


    public static TT18MsgTypeEnum getByType(String type) {
        if (Strings.isEmpty(type)) {
            return null;
        }
        return Arrays.stream(values()).filter(e -> e.getType().equals(type)).findFirst().orElse(null);
    }

    public static TT18MsgTypeEnum getByName(String name) {
        return Arrays.stream(values()).filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
}
