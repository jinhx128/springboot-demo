package com.luoyu.dynamicmultipledatasources.aop;

import com.luoyu.dynamicmultipledatasources.config.DataSourceContextHolder;
import com.luoyu.dynamicmultipledatasources.enums.DataSourceType;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(-1)
public class ChangeDataSourceAspect {

    /**
     * 切换数据源
     */
    @Before("@annotation(changeDataSource)")
    public void changeDataSourceType(JoinPoint point, ChangeDataSource changeDataSource) throws Throwable {
        String value = changeDataSource.value();
        if (value.equals("db1")){
            DataSourceContextHolder.setDataSourceType(DataSourceType.DB1);
        }else if (value.equals("db2")){
            DataSourceContextHolder.setDataSourceType(DataSourceType.DB2);
        }else {
            // 默认使用db1
            DataSourceContextHolder.setDataSourceType(DataSourceType.DB1);
        }

    }

    /**
     * 清除数据源
     */
    @After("@annotation(changeDataSource)")
    public void clearDataSourceType(JoinPoint point, ChangeDataSource changeDataSource) {
        DataSourceContextHolder.clearDataSourceType();
    }

}
