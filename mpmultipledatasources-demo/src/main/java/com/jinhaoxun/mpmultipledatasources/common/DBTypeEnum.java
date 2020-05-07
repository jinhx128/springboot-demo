package com.jinhaoxun.mpmultipledatasources.common;

/**
 * @Description: 数据源枚举类
 * @Author: jinhaoxun
 * @Date: 2020/5/6 下午5:12
 * @Version: 1.0.0
 */
public enum DBTypeEnum {
    /**
     * 数据库1
     */
    DB1("db1"),
    /**
     * 数据库2
     */
    DB2("db2");

    private String value;

    DBTypeEnum(String value){this.value=value;}

    public String getValue() {
        return value;
    }
}
