package com.luoyu.rocketmq.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RocketMQMessageListener(topic = "topic1", selectorExpression = "tag1", consumeMode = ConsumeMode.ORDERLY, consumerGroup = "${rocketmq.consumer.group}")
public class RocketMQConsumerListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        log.info("接收到消息：" + s);
    }

}
