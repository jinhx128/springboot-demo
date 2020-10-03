package com.luoyu.rocketmq.service.impl;

import com.luoyu.rocketmq.entity.request.AddMessageReq;
import com.luoyu.rocketmq.service.RocketMQService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class RocketMQServiceImpl implements RocketMQService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public boolean sendMessage(AddMessageReq addMessageReq) {
        //指定topic，tag
        rocketMQTemplate.convertAndSend(addMessageReq.getTopic() + ":" + addMessageReq.getTag(), addMessageReq.getMessage());
        return true;
    }

    @Override
    public boolean syncSendMessage(AddMessageReq addMessageReq) {
        rocketMQTemplate.asyncSend(addMessageReq.getTopic(), addMessageReq.getMessage(), new SendCallback() {
            // 实现消息发送成功的后续处理
            public void onSuccess(SendResult var1) {
                log.info("async onSucess SendResult：{}", var1);
            }
            // 实现消息发送失败的后续处理
            public void onException(Throwable var1) {
                log.info("async onException Throwable：{}", var1);
            }
        });
        return true;
    }
}
