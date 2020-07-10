package com.luoyu.exception.enumutil;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2019-08-09
 * @description 枚举工具类
 */
public class EnumUtil {

    /**
     * @author jinhaoxun
     * @description 通过code获取枚举类msg方法
     * @param code 枚举类code
     * @param t 对应的枚举类
     * @return Stinrg 对应的枚举类msg
     */
    public static <T extends CodeEnum> String getByCode(Integer code, Class<T> t){
        for(T item: t.getEnumConstants()){
            if(item.getCode().equals(code)){
                return item.getMsg();
            }
        }
        return "系统错误";
    }
}
