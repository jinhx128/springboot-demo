package com.jinhaoxun.exception.vo.http;


import com.jinhaoxun.exception.exception.ResponseMsg;
import com.jinhaoxun.exception.vo.base.Model;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Description: Http的响应大对象
 * @author jinhaoxun
 * @date 2019年12月29日 下午8:15:39
 */
@Setter
@Getter
public class HttpResponse<T> extends Model {
	/**
	 * 响应码
	 */
	private Integer code;
	/**
	 * 响应时间
	 */
	private long time;
	/**
	 * 响应的信息（一般为错误信息）
	 */
	private String msg;
	/**
	 * 响应数据（一般为ActionResponse的子类）
	 */
	private T data;
	/**
	 * 响应的密文，如果该接口需要加密返回，
	 * 则将data的密文绑定到该字段上，
	 * srs和data至少有一个为空
	 */
	private String srs;
	
	/**
	 * 私有化默认构造器
	 */
	private HttpResponse() {
	}
	
	private HttpResponse(Integer code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.time = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();;
	}

	/**
	* @Description: 构建一个响应
	* @author jinhaoxun
	* @date 2019年1月2日 下午2:09:24 
	* @param code
	* @param msg
	* @param data
	* @return
	 */
	public static <T> HttpResponse<T> build(Integer code, String msg, T data){
		return new HttpResponse<T>(code, msg, data);
	}

	/**
	* @Description: 构建一个成功带数据和msg的响应
	* @author jinhaoxun
	* @date 2019年1月2日 下午2:08:52 
	* @param msg
	* @param data
	* @return
	 */
	public static <T> HttpResponse<T> buildSuccess(String msg, T data){
		return build(ResponseMsg.SUCCESS.getCode(), msg, data);
	}

	/**
	 * @Description: 构建一个成功带数据和msg的响应
	 * @author jinhaoxun
	 * @date 2019年1月2日 下午2:08:52
	 * @param data
	 * @return
	 */
	public static <T> HttpResponse<T> buildSuccess(T data){
		return build(ResponseMsg.SUCCESS.getCode(), ResponseMsg.SUCCESS.getMsg(), data);
	}

	/**
	* @Description: 构建一个空的默认的成功响应
	* @author jinhaoxun
	* @date 2019年1月2日 下午2:07:11 
	* @return
	 */
	public static <T> HttpResponse<T> buildSuccess(){
		return buildSuccess(null);
	}

}
