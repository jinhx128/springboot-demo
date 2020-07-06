package com.jinhaoxun.exception.exception;

import com.jinhaoxun.exception.enumutil.EnumUtil;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 异常工厂，各个模块交给Spring管理，无须每次新增实例
 */
@Component
public class ExceptionFactory {

	/**
	 * @author jinhaoxun
	 * @description 工厂生成异常对象方法
	 * @param code 异常状态码
	 * @param log 异常打印日志
	 * @param args 异常返回信息
	 * @return CustomException
	 */
	public CustomException build(int code, String log, String... args) {
		String msg = EnumUtil.getByCode(code, ResponseMsg.class);

		if(args != null && args.length != 0) {
			msg = args[0];
		}
		return new CustomException(code, log, msg);
	}
}
