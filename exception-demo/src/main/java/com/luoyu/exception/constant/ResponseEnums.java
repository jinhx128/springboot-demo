package com.luoyu.exception.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 响应码枚举
 */
@Getter
@AllArgsConstructor
public enum ResponseEnums {

    /**
     * 请求成功
     */
    SUCCESS(200,"请求成功"),
    /**
     * 系统异常，请稍后重试
     */
    EXCEPTION(10000,"系统异常，请稍后重试"),
    /**
     * 请求的资源(网页等)不存在
     */
    NOT_FOUND(404,"请求的资源(网页等)不存在"),

    /**
     * 共6位,1：系统异常码和值定义在该类中，大家公用
     * 前2位表示大板块（10：系统，11：管理后台模块，12：文章模块，13：公共模块，14：上传下载文件模块）
     * 第3,4位表示项目的小模块，5，6位代表具体错误
     */
    /**************************           系统模块             *******************************/
    /**
     * 登陆超时
     */
    LOGIN_TIMEOUT(100001  ,"登陆超时"),
    /**
     * 参数有误
     */
    WRONG_PARAM(100002  ,"参数有误"),
    /**
     * 缺少必要的参数
     */
    MISS_PARAM(100003  ,"缺少必要的参数"),
    /**
     * Hystrix 降级开启抛出异常
     */
    HYSTRIX_THROW_EXCEPTION(100004  ,"请求超时，请稍后重试"),

    /**************************           账号模块             *******************************/
    /**
     * 您没有该权限
     */
    MNG_PERMISSION_DENY(110101,"您没有该权限"),
    /**
     * 密码错误
     */
    PASSWORD_WRONG(110102,"密码错误"),
    /**
     * 用户不存在
     */
    USER_NOT_EXIST(110103,"用户不存在"),
    /**
     * 账号被封禁
     */
    ACCOUNT_IS_BLOCKED(110104,"账号被封禁"),
    /**
     * 账号已注销
     */
    ACCOUNT_IS_CANCELLED(110105,"账号已注销"),
    /**
     * 验证码已过期
     */
    VERIFICATION_CODE_EXPIRED(110106,"验证码已过期"),
    /**
     * 从Redis中获取验证码错误
     */
    GET_CODE_WRONG_FROM_REDIS(110107,"从Redis中获取验证码错误"),
    /**
     * 身份信息已过期
     */
    IDENTITY_INFORMATION_IS_EXPIRED(110108,"身份信息已过期"),
    /**
     * 用户未登录
     */
    USER_NOT_LOG_IN(110109,"用户未登录"),
    /**
     * 用户退出登录失败
     */
    USER_LOG_OUT_FAIL(110110,"用户退出登录失败"),

    /**
     * 密码修改失败
     */
    PASSWORD_CHANGE_FAIL(110112,"密码修改失败"),
    /**
     * 账号封禁失败
     */
    ACCOUNT_BLOCK_FAIL(110113,"账号封禁失败"),
    /**
     * 账号解封失败
     */
    ACCOUNT_UNSEALING_FAIL(110114,"账号解封失败"),
    /**
     * 账号注册失败
     */
    ACCOUNT_REGISTRATION_FAIL(110115,"账号注册失败"),
    /**
     * 获取手机验证码失败
     */
    GET_PHONE_CODE_FAIL(110116,"获取手机验证码失败"),
    /**
     * 获取邮箱验证码失败
     */
    GET_EMAIL_CODE_FAIL(110117,"获取邮箱验证码失败"),
    /**
     * 获取用户信息失败
     */
    GET_USERINFO_FAIL(110118,"获取用户信息失败"),
    /**
     * 更新用户信息失败
     */
    UPDATE_USERINFO_FAIL(110119,"更新用户信息失败"),
    /**
     * 账号注销失败
     */
    ACCOUNT_CANAEL_FAIL(110120,"账号注销失败"),
    /**
     * 重复获取验证码
     */
    REPEAT_GET_USER_LOG_IN_CODE(110121,"重复获取验证码"),
    /**
     * 验证码已过期
     */
    USER_LOG_IN_CODE_EXPIRATIONED(110122,"验证码已过期"),
    /**
     * 验证码错误
     */
    USER_LOG_IN_CODE_WRONG(110123,"验证码错误"),
    ;

    public Integer code;
    public String msg;

}