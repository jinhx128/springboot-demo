package com.luoyu.rabbitmq.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * RabbitMqUtils
 *
 * @author luoyu
 * @date 2019/03/16 22:08
 * @description
 */
@Slf4j
@Component
public class RabbitMqUtils implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    private RabbitTemplate rabbitTemplate;

    /**
     * 构造方法注入
     */
    @Autowired
    public RabbitMqUtils(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        //这是是设置回调能收到发送到响应
        rabbitTemplate.setConfirmCallback(this);
        //如果设置备份队列则不起作用
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback(this);
    }

    /**
     * 回调确认
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if(ack){
            log.info("消息发送成功：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        }else{
            log.info("消息发送失败：correlationData({}),ack({}),cause({})",correlationData,ack,cause);
        }
    }

    /**
     * 消息发送到转换器的时候没有对列,配置了备份对列该回调则不生效
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息丢失：exchange({}),route({}),replyCode({}),replyText({}),message:{}",exchange,routingKey,replyCode,replyText,message);
    }

    /**
     * 发送到指定Queue
     * @param queueName
     * @param obj
     */
    public void send(String queueName, Object obj){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(queueName, obj, correlationId);
    }

    /**
     * 1、交换机名称
     * 2、routingKey
     * 3、消息内容
     */
    public void sendByRoutingKey(String exChange, String routingKey, Object obj){
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());
        this.rabbitTemplate.convertAndSend(exChange, routingKey, obj, correlationId);
    }
}
