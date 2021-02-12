package com.luoyu.dynamicmultipledatasources.config;

import com.luoyu.dynamicmultipledatasources.enums.DataSourceType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
//basePackages 我们接口文件的地址
@MapperScan(basePackages = "com.luoyu.dynamicmultipledatasources.mapper", sqlSessionFactoryRef = "SqlSessionFactory")
public class DynamicDataSourceConfig {

    // 将这个对象放入Spring容器中
    @Bean("db1DataSource")
    // 表示这个数据源是默认数据源
    @Primary
    // 读取配置参数映射成为一个对象
    @ConfigurationProperties(prefix = "spring.datasource.db1", ignoreUnknownFields = false)
    public DataSource getDB1DateSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean("db2DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.db2", ignoreUnknownFields = false)
    public DataSource getDB2DateSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("db1DataSource") DataSource db1DataSource,
                                        @Qualifier("db2DataSource") DataSource db2DataSource) {
        // 这个地方是比较核心的targetDataSource集合是我们数据库和名字之间的映射
        Map<Object, Object> targetDataSource = new HashMap<>();
        targetDataSource.put(DataSourceType.DB1, db1DataSource);
        targetDataSource.put(DataSourceType.DB2, db2DataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSource);
        // 设置默认对象
        dataSource.setDefaultTargetDataSource(db1DataSource);
        return dataSource;
    }

    @Bean("transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dynamicDataSource) {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Bean("SqlSessionFactory")
    public SqlSessionFactory SqlSessionFactory(@Qualifier("dynamicDataSource") DataSource dynamicDataSource, @Qualifier("mybatisplusConfiguration") org.apache.ibatis.session.Configuration configuration)
            throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dynamicDataSource);
        // 使mybatis配置生效，加载顺序问题
        bean.setConfiguration(configuration);
        bean.setMapperLocations(
                // 设置我们的xml文件路径
                new PathMatchingResourcePatternResolver().getResources("classpath*:com/luoyu/dynamicmultipledatasources/mapper/xml/*.xml"));
        bean.setTypeAliasesPackage("com.luoyu.dynamicmultipledatasources.entity");
        return bean.getObject();
    }

}