package com.luoyu.aop.aop;

import java.lang.annotation.*;

/**
 *
* @Description: http请求的参数校验
* @author luoyu
* @date 2019年1月9日 下午6:53:41
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HttpCheck {

    /**
     * 是否需要解密（默认需要解密）
     */
    boolean isDecrypt() default true;

    /**
     * 解密的key，只有当isDecrypt=true
     * 才会检测该字段，并且传入为空时，
     * 用系统预先设置的key进行解密。
     */
    String decryptKey() default "";

    /**
     * 解密，系统统一加密反序列化的类
     */
    Class<?>  dataType();

    /**
     * 是否需要加密返回(默认加密返回)
     */
    boolean isEncrypt() default true;

    /**
     * 加密的key，只有当isEncrypt=true
     * 才会检测该字段，并且传入为空时，
     * 用系统预先设置的key进行加密返回
     */
    String encryptKey() default "";

    /**
     * 是否需要检测超时时间(默认需要)
     */
    boolean isTimeout() default true;

    /**
     * 超时时间，只有当isTimeout=true
     * 才会检测该字段，并且传入为空时，
     * 用系统预先设置的timeout进行加密返回
     */
    String timeout() default "";

}
