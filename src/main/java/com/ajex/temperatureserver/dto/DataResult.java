package com.ajex.temperatureserver.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResult implements Serializable {
    private String servertime;
    private String downcmd;

}
