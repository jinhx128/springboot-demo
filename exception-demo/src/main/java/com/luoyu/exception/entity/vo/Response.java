package com.luoyu.exception.entity.vo;

import com.luoyu.exception.constant.ResponseEnums;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Response
 *
 * @author luoyu
 * @date 2018/10/07 13:28
 * @description 通用返回类
 */
@Data
public class Response implements Serializable {

    private String msg;
    private int code;
    private Object data;
    private String time;

    private Response() {
    }

    private Response(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    private Response(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.time = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
    }

    public static Response success() {
        return new Response(ResponseEnums.SUCCESS.getCode(), ResponseEnums.SUCCESS.getMsg());
    }

    public static Response success(Object data) {
        return new Response(ResponseEnums.SUCCESS.getCode(), ResponseEnums.SUCCESS.getMsg(), data);
    }

    public static Response success(Object data, String msg) {
        return new Response(ResponseEnums.SUCCESS.getCode(), msg, data);
    }

    public static Response fail() {
        return new Response(ResponseEnums.EXCEPTION.getCode(), ResponseEnums.EXCEPTION.getMsg());
    }

    public static Response fail(ResponseEnums responseEnums) {
        return new Response(responseEnums.getCode(), responseEnums.getMsg());
    }

    public static Response fail(ResponseEnums responseEnums, Object data) {
        return new Response(responseEnums.getCode(), responseEnums.getMsg(), data);
    }

    public static Response fail(int code, String msg) {
        return new Response(code, msg);
    }

    public static Response fail(int code, String msg, Object data) {
        return new Response(code, msg, data);
    }

}
