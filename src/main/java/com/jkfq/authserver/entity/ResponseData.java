package com.jkfq.authserver.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 通用返回类
 *
 * @Author
 * @create 2019-01-31
 **/
@Data
public class ResponseData<T> implements Serializable {

    private static final long serialVersionUID = -2352918700533956949L;

    /**
     * 返回码
     */
    private Integer code;
    /**
     * 返回描述
     */
    private String message;

    private T data;

    public ResponseData() {
    }

    public ResponseData(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseData(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
