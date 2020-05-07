package com.jinhaoxun.mpmultipledatasources.config;

import com.jinhaoxun.mpmultipledatasources.common.DBTypeEnum;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/5/7 下午3:26
 * @Version: 1.0.0
 */
public class DbContextHolder {

    private static final ThreadLocal contextHolder = new ThreadLocal<>();
    /**
     * 设置数据源
     * @param dbTypeEnum
     */
    public static void setDbType(DBTypeEnum dbTypeEnum) {
        contextHolder.set(dbTypeEnum.getValue());
    }

    /**
     * 取得当前数据源
     * @return
     */
    public static String getDbType() {
        return (String) contextHolder.get();
    }

    /**
     * 清除上下文数据
     */
    public static void clearDbType() {
        contextHolder.remove();
    }
}
