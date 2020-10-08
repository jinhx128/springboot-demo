package com.luoyu.rabbitmq.listener;

import com.luoyu.rabbitmq.constants.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

@Slf4j
@Component
public class RabbitMqListener {

    @RabbitListener(queues = RabbitMqConstants.TEST1_QUEUE)
    public void test1Consumer(Message message, Channel channel) {
        try {
            //手动确认消息已经被消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("Test1消费消息：" + message.toString() + "。成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Test1消费消息：" + message.toString() + "。失败！");
        }
    }

    @RabbitListener(queues = RabbitMqConstants.TEST2_QUEUE)
    public void test2Consumer(Message message, Channel channel) {
        try {
            //手动确认消息已经被消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            log.info("Test2消费消息：" + message.toString() + "。成功！");
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Test2消费消息：" + message.toString() + "。失败！");
        }
    }

}
