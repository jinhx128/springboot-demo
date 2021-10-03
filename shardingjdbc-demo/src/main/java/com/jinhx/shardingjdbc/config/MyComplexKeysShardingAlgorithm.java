package com.jinhx.shardingjdbc.config;

import com.jinhx.shardingjdbc.util.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 配置Sharding-JDBC复合分片算法
 * 根据id和age计算，来确定是路由到那个表中
 * 目前处理 = 和 in 操作，其余的操作，比如 >、< 等范围操作均不支持。
 *
 * @author jinhx
 * @since 2021-07-27
 */
@Slf4j
public class MyComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    /**
     * orderId
     */
    private static final String COLUMN_ORDER_ID = "order_id";

    /**
     * userId
     */
    private static final String COLUMN_USER_ID = "user_id";

    /**
     * 重写复合分片算法
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {
        if (!shardingValue.getColumnNameAndRangeValuesMap().isEmpty()) {
            throw new RuntimeException("条件全部为空，无法路由到具体的表，暂时不支持范围查询");
        }

        // 获取orderId
        Collection<Long> orderIds = shardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_ORDER_ID, new ArrayList<>(1));
        // 获取userId
        Collection<Long> userIds = shardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_USER_ID, new ArrayList<>(1));

        if (CollectionUtils.isEmpty(orderIds) && CollectionUtils.isEmpty(userIds)) {
            throw new RuntimeException("orderId，userId字段同时为空，无法路由到具体的表，暂时不支持范围查询");
        }

        // 获取最终要查询的表后缀序号的集合，入参顺序不能颠倒
        List<Integer> tableNos = getTableNoList(orderIds, userIds);

        return tableNos.stream()
                // 对可用的表数量求余数，获取到真实的表的后缀
//                .map(idSuffix -> String.valueOf(idSuffix % availableTargetNames.size()))
                // 拼接获取到真实的表
                .map(tableSuffix -> availableTargetNames.stream().filter(targetName -> targetName.endsWith(String.valueOf(tableSuffix))).findFirst().orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 获取最终要查询的表后缀序号的集合
     *
     * @param orderIds orderId字段集合
     * @param userIds userId字段集合
     * @return 最终要查询的表后缀序号的集合
     */
    private List<Integer> getTableNoList(Collection<Long> orderIds, Collection<Long> userIds) {
        List<Integer> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(orderIds)){
            // 获取表位信息
            result.addAll(orderIds.stream()
                    .filter(item -> Objects.nonNull(item) && item > 0)
                    .map(item -> (int) SnowFlakeUtil.getDataCenterId(item))
                    .collect(Collectors.toList()));
        }

        if (CollectionUtils.isNotEmpty(userIds)) {
            // 获取表位信息
            result.addAll(userIds.stream().filter(item -> Objects.nonNull(item) && item > 0)
                    .map(item -> (int) SnowFlakeUtil.getDataCenterId(item))
                    .collect(Collectors.toList()));
        }

        if (CollectionUtils.isNotEmpty(result)) {
            log.info("SharingJDBC解析路由表后缀成功 redEnvelopeIds={} uids={} 路由表后缀列表={}", orderIds, userIds, result);
            // 合并去重
            return result.stream().distinct().collect(Collectors.toList());
        }
        log.error("SharingJDBC解析路由表后缀失败 redEnvelopeIds={} uids={}", orderIds, userIds);
        throw new RuntimeException("orderId，userId解析路由表后缀为空，无法路由到具体的表，暂时不支持范围查询");
    }

}