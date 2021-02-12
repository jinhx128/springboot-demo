package com.luoyu.staticmultipledatasources.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/5/9 8:27 下午
 * @Version: 1.0.0
 */
@Configuration
@MapperScan(basePackages = {"com.luoyu.staticmultipledatasources.mapper.db1"}, sqlSessionTemplateRef ="db1SqlSessionTemplate")
public class DB1DataSourceConfig {

    /**
     * 创建数据源
     * @return
     */
    @Bean(name = "db1DataSource")
    @ConfigurationProperties(prefix="spring.datasource.db1")
    @Primary
    public DataSource getDB1DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建SessionFactory
     * @param db1DataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "db1SqlSessionFactory")
    @Primary
    public SqlSessionFactory db1SqlSessionFactory(@Qualifier("db1DataSource") DataSource db1DataSource, @Qualifier("mybatisplusConfiguration") org.apache.ibatis.session.Configuration configuration) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(db1DataSource);
        // 使mybatis-plus配置生效，加载顺序问题
        bean.setConfiguration(configuration);

        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/luoyu/staticmultipledatasources/mapper/db1/xml/*.xml"));
        bean.setTypeAliasesPackage("com.luoyu.staticmultipledatasources.entity");

        return bean.getObject();
    }

    /**
     * 创建事务管理器
     * @param db1DataSource
     * @return
     */
    @Bean("db1TransactionManger")
    @Primary
    public DataSourceTransactionManager db1TransactionManger(@Qualifier("db1DataSource") DataSource db1DataSource){
        return new DataSourceTransactionManager(db1DataSource);
    }

    /**
     * 创建SqlSessionTemplate
     * @param db1SqlSessionFactory
     * @return
     */
    @Bean(name = "db1SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db1SqlSessionFactory") SqlSessionFactory db1SqlSessionFactory){
        return new SqlSessionTemplate(db1SqlSessionFactory);
    }

}
