package com.ajex.temperatureserver.enums;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;
import java.util.Arrays;

@Getter
public enum CollectionEnum  implements Serializable {
    GET_SERVER_TIME("1", "msg_other_data"),
    HEART_BEAT("2", "msg_other_data"),
    STANDARD_DATA("3", "msg_standard_data"),
    SERVER_CONFIG("@CMD,", "msg_server_downward_commands_setting"),
    MACHINE_REPLY_AFTER_RECEIVE_SERVER_CONFIG("4", "msg_server_downward_commands_setting"),
    MACHINE_VERSION("5", "msg_other_data"),
    REPLY_TAG_CONFIG("6","msg_other_data");

    private String type;

    private String name;

    CollectionEnum(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public static CollectionEnum getByType(String type) {
        if (Strings.isEmpty(type)) {
            return null;
        }
        return Arrays.stream(values()).filter(e -> e.getType().equals(type)).findFirst().orElse(null);
    }
}
