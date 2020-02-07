package com.jinhaoxun.rocketmqdemo;

import com.jinhaoxun.rocketmqdemo.request.AddMessageReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class RocketmqDemoApplicationTests {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void sendMessageTest() throws InterruptedException {
        AddMessageReq addMessageReq = new AddMessageReq();
        addMessageReq.setTopic("topic1");
        addMessageReq.setTag("tag1");
        addMessageReq.setMessage("哈哈哈");
        rocketMQTemplate.convertAndSend(addMessageReq.getTopic(), addMessageReq.getMessage());
        // 让主线程睡眠10秒
        Thread.currentThread().sleep(10000);
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
