package com.jinhaoxun.rocketmqdemo;

import com.jinhaoxun.rocketmqdemo.message.Message;
import com.jinhaoxun.rocketmqdemo.request.AddMessageReq;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class RocketmqDemoApplicationTests {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Test
    void syncSendMessageTest() throws InterruptedException {
        //发送同步消息
        Message<String> message = new Message<>();
        message.setId("123");
        message.setContent("测试一下");
        rocketMQTemplate.asyncSend("topic2", message, new SendCallback() {
                    // 实现消息发送成功的后续处理
                    public void onSuccess(SendResult var1) {
                        System.out.printf("async onSucess SendResult=%s %n", var1);
                    }
                    // 实现消息发送失败的后续处理
                    public void onException(Throwable var1) {
                        System.out.printf("async onException Throwable=%s %n", var1);
                    }
                });
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
        rocketMQTemplate.convertAndSend(addMessageReq.getTopic() + ":" + addMessageReq.getTag(), addMessageReq.getMessage());
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
