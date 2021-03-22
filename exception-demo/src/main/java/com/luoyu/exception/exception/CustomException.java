package com.luoyu.exception.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 自定义统一异常（相当于业务异常）
 */
@Data
public class CustomException extends Exception implements Serializable {

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
	public CustomException(Integer code, String log, String msg) {
		super(msg);
		this.code = code;
		this.log = log;
	}

}
