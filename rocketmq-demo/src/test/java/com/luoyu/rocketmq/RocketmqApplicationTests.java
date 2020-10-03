package com.luoyu.rocketmq;

import com.luoyu.rocketmq.entity.Message;
import com.luoyu.rocketmq.entity.request.AddMessageReq;
import com.luoyu.rocketmq.service.RocketMQService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class RocketmqApplicationTests {

    @Autowired
    private RocketMQService rocketMQService;

    @Test
    void syncSendMessageTest() throws InterruptedException {
        //发送同步消息
        Message<String> message = new Message<>();
        message.setId("123");
        message.setContent("测试一下");

        AddMessageReq addMessageReq = new AddMessageReq();
        addMessageReq.setTopic("topic2");
        addMessageReq.setMessage(message);
        rocketMQService.syncSendMessage(addMessageReq);
        // 让主线程睡眠10秒
        Thread.currentThread().sleep(10000);
    }

    @Test
    void sendMessageTest() throws InterruptedException {
        //指定topic，tag
        AddMessageReq addMessageReq = new AddMessageReq();
        addMessageReq.setTopic("topic1");
        addMessageReq.setTag("tag1");

        Message<String> message = new Message<>();
        message.setId("123");
        message.setContent("测试一下");
        addMessageReq.setMessage(message);

        rocketMQService.sendMessage(addMessageReq);
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
