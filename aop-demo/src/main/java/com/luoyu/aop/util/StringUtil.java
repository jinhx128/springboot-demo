package com.luoyu.aop.util;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/16 12:21 上午
 * @Version: 1.0.0
 */
public class StringUtil {

    /**
     * @author jinhaoxun
     * @description String为空判断(不允许空格)方法
     * @param str 要进行判断的数据
     * @return boolean 是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

}
