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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMqConstants.TEST1_QUEUE, durable = "true"),
            exchange = @Exchange(
                    value = RabbitMqConstants.EXCHANGE_NAME,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {RabbitMqConstants.TOPIC_TEST1_ROUTINGKEY}))
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

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = RabbitMqConstants.TEST2_QUEUE, durable = "true"),
            exchange = @Exchange(
                    value = RabbitMqConstants.EXCHANGE_NAME,
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC
            ),
            key = {RabbitMqConstants.TOPIC_TEST2_ROUTINGKEY}))
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
