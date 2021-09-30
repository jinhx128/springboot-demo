package com.jinhx.shardingjdbc.config;

import com.jinhx.shardingjdbc.util.SnowFlakeUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.spi.keygen.ShardingKeyGenerator;

import java.util.Properties;

/**
 * 自定义Sharding-JDBC生成分布式主键id
 *
 * @author jinhx
 * @date 2021-07-27
 */
@Setter
@Getter
@Slf4j
public class MyShardingKeyGenerator implements ShardingKeyGenerator {

    private Properties properties = new Properties();

    @Override
    public Comparable<?> generateKey() {
        Long id = SnowFlakeUtil.getSnowflakeId();
        log.info("自定义的id：{}", id);
        return 1;
    }

    @Override
    public String getType() {
        return "MyShardingKeyGenerator";
    }

}
