package com.jinhx.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinhx.shardingjdbc.entity.Order;

import java.util.List;

/**
 * IOrderService
 *
 * @author jinhx
 * @since 2021-07-27
 */
public interface IOrderService extends IService<Order> {

    /**
     * 根据orderIds查询
     *
     * @param orderIds orderIds
     * @return List<Order>
     */
    List<Order> selectByOrderIds(List<Long> orderIds);

    /**
     * 根据userIds查询
     *
     * @param userIds userIds
     * @return List<Order>
     */
    List<Order> selectByUserIds(List<Long> userIds);

    /**
     * 批量插入
     *
     * @param orders orders
     */
    void insertOrders(List<Order> orders);

}
