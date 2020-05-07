package com.jinhaoxun.mpmultipledatasources.aop;

import com.jinhaoxun.mpmultipledatasources.common.DBTypeEnum;
import com.jinhaoxun.mpmultipledatasources.config.DbContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/5/7 下午3:26
 * @Version: 1.0.0
 */
@Component
@Order(value = -100)
@Slf4j
@Aspect
public class DataSourceSwitchAspect {

    @Pointcut("execution(* com.jinhaoxun.mpmultipledatasources.mapper.db1..*.*(..))")
    private void db1Aspect() {
    }

    @Pointcut("execution(* com.jinhaoxun.mpmultipledatasources.mapper.db2..*.*(..))")
    private void db2Aspect() {
    }

//    @Pointcut("execution(* com.df.openapi.*.mapper.db3..*.*(..))")
//    private void db3Aspect() {
//    }

    @Before("db1Aspect()")
    public void db1() {
        log.info("切换到db1 数据源...");
        DbContextHolder.setDbType(DBTypeEnum.DB1);
    }

    @Before("db2Aspect()")
    public void db2() {
        log.info("切换到db2 数据源...");
        DbContextHolder.setDbType(DBTypeEnum.DB2);
    }

//    @Before("db3Aspect()")
//    public void db3() {
//        log.info("切换到db3 数据源...");
//        DbContextHolder.setDbType(DBTypeEnum.db3);
//    }
}

