package com.jinhx.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinhx.shardingjdbc.entity.Order;
import com.jinhx.shardingjdbc.mapper.OrderMapper;
import com.jinhx.shardingjdbc.service.IOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * OrderServiceImpl
 *
 * @author jinhx
 * @since 2021-07-27
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    /**
     * 根据orderIds查询
     *
     * @param orderIds orderIds
     * @return List<Order>
     */
    @Override
    public List<Order> selectByOrderIds(List<Long> orderIds) {
        return baseMapper.selectBatchIds(orderIds);
    }

    /**
     * 根据userIds查询
     *
     * @param userIds userIds
     * @return List<Order>
     */
    @Override
    public List<Order> selectByUserIds(List<Long> userIds) {
        return baseMapper.selectList(new LambdaQueryWrapper<Order>()
                .in(CollectionUtils.isNotEmpty(userIds), Order::getUserId, userIds));
    }

    /**
     * 批量插入
     *
     * @param orders orders
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertOrders(List<Order> orders) {
        if (CollectionUtils.isNotEmpty(orders)){
            if (orders.stream().mapToInt(item -> baseMapper.insert(item)).sum() != orders.size()){
                log.error("批量插入order表失败 orders={}" + orders);
                throw new RuntimeException("批量插入order表失败");
            }
        }
    }

}
