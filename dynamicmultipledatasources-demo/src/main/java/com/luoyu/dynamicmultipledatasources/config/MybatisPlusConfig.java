package com.luoyu.dynamicmultipledatasources.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Description: MybatisPlus配置类
 * @Author: jinhaoxun
 * @Date: 2020/2/13 上午11:34
 * @Version: 1.0.0
 */
@EnableTransactionManagement
@Configuration
@Order(-1)
public class MybatisPlusConfig {

    /**
     * 使application.properties配置生效，如果不主动配置，由于@Order配置顺序不同，将导致配置不能及时生效，多数据源配置驼峰法生效
     * @return 数据源
     */
    @Bean("mybatisplusConfiguration")
    @ConfigurationProperties(prefix = "mybatis-plus.configuration")
    @Scope("prototype")
    public org.apache.ibatis.session.Configuration globalConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

    /**
     * mybatis-plus SQL执行效率插件【生产环境可以关闭】，设置 dev test 环境开启
     */
//    @Bean
//    @Profile({"dev", "qa"})
//    public PerformanceInterceptor performanceInterceptor() {
//        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
//        performanceInterceptor.setMaxTime(1000);
//        performanceInterceptor.setFormat(true);
//        return performanceInterceptor;
//    }

    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

}