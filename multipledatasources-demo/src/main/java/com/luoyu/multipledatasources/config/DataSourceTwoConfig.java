package com.luoyu.multipledatasources.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/5/9 8:26 下午
 * @Version: 1.0.0
 */
@Configuration
@MapperScan(basePackages = "com.luoyu.multipledatasources.mapper.db2",sqlSessionTemplateRef ="twoSqlSessionTemplate")
public class DataSourceTwoConfig {

    /**
     * 创建数据源
     * @return
     */
    @Bean(name = "twoDS")
    @ConfigurationProperties(prefix="spring.datasource.db2")
    public DataSource getTwoDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建SessionFactory
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "twoSqlSessionFactory")
    public SqlSessionFactory twoSqlSessionFactory(@Qualifier("twoDS") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        bean.setMapperLocations(resolver.getResources("classpath*:com/luoyu/multipledatasources/mapper/db2/xml/*.xml"));

        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        // 开启驼峰命名规则
//        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);

        //分页插件
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

        Properties properties = new Properties();
        //数据库
        properties.setProperty("helperDialect", "mysql");
//        //是否将参数offset作为PageNum使用
        properties.setProperty("offsetAsPageNum", "true");
//        //是否进行count查询
        properties.setProperty("rowBoundsWithCount", "true");
        //是否分页合理化
        properties.setProperty("reasonable", "true");

        paginationInterceptor.setProperties(properties);

        bean.setPlugins(new PaginationInterceptor[]{paginationInterceptor});

        return bean.getObject();
    }


    /**
     * 创建事务管理器
     * @param dataSource
     * @return
     */
    @Bean("twoTransactionManger")
    public DataSourceTransactionManager twoTransactionManger(@Qualifier("twoDS") DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 创建SqlSessionTemplate
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = "twoSqlSessionTemplate")
    public SqlSessionTemplate wotingSqlSessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
