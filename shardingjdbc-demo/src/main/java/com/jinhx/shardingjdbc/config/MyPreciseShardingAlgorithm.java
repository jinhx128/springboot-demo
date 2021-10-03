package com.jinhx.shardingjdbc.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 重写Sharding-JDBC分片算法
 *
 * @author jinhx
 * @since 2021-07-27
 */
@Slf4j
public class MyPreciseShardingAlgorithm implements PreciseShardingAlgorithm<Integer> {

    /**
     * 重写分片算法
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String tableName : availableTargetNames) {
            if (tableName.endsWith(shardingValue.getValue() % availableTargetNames.size() + "")) {
                return tableName;
            }
        }
        throw new RuntimeException("无法路由到具体的表");
    }

}