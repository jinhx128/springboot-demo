package com.jinhx.shardingjdbc;

import com.jinhx.shardingjdbc.entity.Order;
import com.jinhx.shardingjdbc.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class ShardingjdbcApplicationTests {

    @Autowired
    private IOrderService iOrderService;

    @Test
    void selectByOrderIdsTest() {
        List<Long> orderIds = Lists.newArrayList(1443844581547311109L, 1443844581547442181L, 1443844581547573255L, 1443844581547704327L);
        log.info(iOrderService.selectByOrderIds(orderIds).toString());
    }

    @Test
    void selectByUserIdsTest() {
        List<Long> userIds = Lists.newArrayList(1443844581547311108L, 1443844581547311106L, 1443844581547442180L, 1443844581547704326L);
        log.info(iOrderService.selectByUserIds(userIds).toString());
    }

    @Test
    void insertOrdersTest() {
        List<Order> orders = Lists.newArrayList();
        for (int i = 1;i < 100;i++){
            Order order = new Order();
            order.buildUserId(i);
            order.setMoney(i * 1000L);
            order.buildOrderId();
            orders.add(order);
        }
        log.info("orders={}", orders);
        iOrderService.insertOrders(orders);
    }

    @BeforeEach
    void testBefore(){
        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter(){
        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
