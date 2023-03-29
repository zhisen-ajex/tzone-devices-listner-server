package com.ajex.temperatureserver.dto;



import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@ToString
public class ResponseResult<T> implements Serializable {
    /**
     * 状态编码 0正常;1 错误
     */
    private Integer sta;
    private T data;//返回数据区
    private String error;//错误提示


    public ResponseResult() {

    }



    public static ResponseResult success() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSta(0);
        responseResult.setError("");
        return responseResult;
    }

    public static ResponseResult success(String message) {
        ResponseResult responseResult = success();
        responseResult.setSta(0);
        responseResult.setData(message);
        responseResult.setError("");
        return responseResult;
    }
    public static ResponseResult success(DataResult dataResult) {
        ResponseResult responseResult = success();
        responseResult.setSta(0);
        responseResult.setData(dataResult);
        responseResult.setError("");
        return responseResult;
    }



    public static ResponseResult error() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSta(1);
        return responseResult;
    }

    public static ResponseResult error(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setSta(1);
        responseResult.setError(message);
        return responseResult;
    }

}
