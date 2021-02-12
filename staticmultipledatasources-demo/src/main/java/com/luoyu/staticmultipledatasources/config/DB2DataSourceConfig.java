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
 * @Date: 2020/5/9 8:26 下午
 * @Version: 1.0.0
 */
@Configuration
@MapperScan(basePackages = "com.luoyu.staticmultipledatasources.mapper.db2",sqlSessionTemplateRef ="db2SqlSessionTemplate")
public class DB2DataSourceConfig {

    /**
     * 创建数据源
     * @return
     */
    @Bean(name = "db2DataSource")
    @ConfigurationProperties(prefix="spring.datasource.db2")
    @Primary
    public DataSource getDB2DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 创建SessionFactory
     * @param db2DataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "db2SqlSessionFactory")
    @Primary
    public SqlSessionFactory db2SqlSessionFactory(@Qualifier("db2DataSource") DataSource db2DataSource, @Qualifier("mybatisplusConfiguration") org.apache.ibatis.session.Configuration configuration) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(db2DataSource);
        // 使mybatis-plus配置生效，加载顺序问题
        bean.setConfiguration(configuration);

        // mapper的xml形式文件位置必须要配置，不然将报错：no statement （这种错误也可能是mapper的xml中，namespace与项目的路径不一致导致）
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:com/luoyu/staticmultipledatasources/mapper/db2/xml/*.xml"));
        bean.setTypeAliasesPackage("com.luoyu.staticmultipledatasources.entity");

        return bean.getObject();
    }

    /**
     * 创建事务管理器
     * @param db2DataSource
     * @return
     */
    @Bean("db2TransactionManger")
    @Primary
    public DataSourceTransactionManager db2TransactionManger(@Qualifier("db2DataSource") DataSource db2DataSource){
        return new DataSourceTransactionManager(db2DataSource);
    }

    /**
     * 创建SqlSessionTemplate
     * @param db2SqlSessionFactory
     * @return
     */
    @Bean(name = "db2SqlSessionTemplate")
    @Primary
    public SqlSessionTemplate db1SqlSessionTemplate(@Qualifier("db2SqlSessionFactory") SqlSessionFactory db2SqlSessionFactory){
        return new SqlSessionTemplate(db2SqlSessionFactory);
    }

}
