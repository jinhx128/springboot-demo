package com.jinhx.shardingjdbc.config;

import com.jinhx.shardingjdbc.entity.User;
import com.jinhx.shardingjdbc.util.SnowFlakeUtil;
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
 * @date 2021-07-27
 */
public class MyComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

    /**
     * id
     */
    private static final String COLUMN_ID = "id";

    /**
     * 年龄
     */
    private static final String COLUMN_AGE = "age";

    /**
     * 重写复合分片算法
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {
        if (!shardingValue.getColumnNameAndRangeValuesMap().isEmpty()) {
            throw new RuntimeException("id，age同时为空，无法路由到具体的表，暂时不支持范围查询");
        }

        // 获取id
        Collection<Long> ids = shardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_ID, new ArrayList<>(1));
        // 获取age
        Collection<Long> ages = shardingValue.getColumnNameAndShardingValuesMap().getOrDefault(COLUMN_AGE, new ArrayList<>(1));

        // 获取最终要查询的表后缀序号的集合，入参顺序不能颠倒
        List<Integer> tableNos = getTableNoList(ids, ages);

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
     * @param ids id主键字段集合
     * @param ages ages字段集合
     * @return 最终要查询的表后缀序号的集合
     */
    private List<Integer> getTableNoList(Collection<Long> ids, Collection<Long> ages) {
        List<Integer> result = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(ids)){
            // 先右移12位，再根据2^2-1按位与取表位的2位
            result.addAll(ids.stream()
                    .filter(item -> Objects.nonNull(item) && item > 0)
                    .map(item -> (int) SnowFlakeUtil.getDataCenterId(item))
                    .collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(ages)) {
            // 进行取模，此处使用根据2^2-1按位与取模，因为分表的数量刚好是2的n次方
            result.addAll(ages.stream().filter(item -> Objects.nonNull(item) && item > 0)
                    .map(item -> (int)(item & (User.TABLE_COUNT - 1)))
                    .collect(Collectors.toList()));
        }
        if (CollectionUtils.isNotEmpty(result)) {
            // 合并去重
            return result.stream().distinct().collect(Collectors.toList());
        }
        throw new RuntimeException("id，age同时为空，无法路由到具体的表，暂时不支持范围查询");
    }

}