package com.luoyu.dynamicmultipledatasources.aop;

import java.lang.annotation.*;

/**
 * 切换数据注解 可以用于类或者方法级别 方法级别优先级 > 类级别
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ChangeDataSource {

    //该值即key值，默认使用默认数据库
    String value() default "db1";

}
