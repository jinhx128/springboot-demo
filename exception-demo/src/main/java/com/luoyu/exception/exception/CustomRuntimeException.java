package com.luoyu.exception.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2019-08-09
 * @description 自定义运行时异常（相当于系统异常）
 */
@Setter
@Getter
@ToString
public class CustomRuntimeException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;

    private String log;

    /**
     * @author jinhaoxun
     * @description 构造器
     * @param code 异常状态码
     * @param log 异常打印日志
     * @param msg 异常返回信息
     */
    public CustomRuntimeException(Integer code, String msg, String log) {
        super(msg);
        this.code = code;
        this.log = log;
    }

    /**
     * @author jinhaoxun
     * @description 构造器
     * @param code 异常状态码
     * @param log 异常打印日志
     */
    public CustomRuntimeException(Integer code, String log) {
        super();
        this.code = code;
        this.log = log;
    }
}
