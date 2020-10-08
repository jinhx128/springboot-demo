package com.luoyu.rabbitmq.config;

import com.luoyu.rabbitmq.constants.RabbitMqConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * RabbitMQConfig
 *
 * @author luoyu
 * @date 2019/03/16 21:59
 * @description
 */
@Slf4j
@Configuration
public class RabbitMqConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     *  声明交换机
     */
    @Bean(RabbitMqConstants.EXCHANGE_NAME)
    public Exchange exchange(){
        //durable(true) 持久化，mq重启之后交换机还在
        return ExchangeBuilder.topicExchange(RabbitMqConstants.EXCHANGE_NAME).durable(true).build();
    }

    /**
     *  声明队列
     *  new Queue(QUEUE_EMAIL,true,false,false)
     *  durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列
     *  auto-delete 表示消息队列没有在使用时将被自动删除 默认是false
     *  exclusive  表示该消息队列是否只在当前connection生效,默认是false
     */
    @Bean(RabbitMqConstants.TEST1_QUEUE)
    public Queue esQueue() {
        return new Queue(RabbitMqConstants.TEST1_QUEUE);
    }

    /**
     *  声明队列
     */
    @Bean(RabbitMqConstants.TEST2_QUEUE)
    public Queue gitalkQueue() {
        return new Queue(RabbitMqConstants.TEST2_QUEUE);
    }

    /**
     *  TEST1_QUEUE队列绑定交换机，指定routingKey
     */
    @Bean
    public Binding bindingEs(@Qualifier(RabbitMqConstants.TEST1_QUEUE) Queue queue,
                             @Qualifier(RabbitMqConstants.EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMqConstants.TOPIC_TEST1_ROUTINGKEY).noargs();
    }

    /**
     *  TEST2_QUEUE队列绑定交换机，指定routingKey
     */
    @Bean
    public Binding bindingGitalk(@Qualifier(RabbitMqConstants.TEST2_QUEUE) Queue queue,
                                 @Qualifier(RabbitMqConstants.EXCHANGE_NAME) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(RabbitMqConstants.TOPIC_TEST2_ROUTINGKEY).noargs();
    }

    /**
     * 如果需要在生产者需要消息发送后的回调，
     * 需要对rabbitTemplate设置ConfirmCallback对象，
     * 由于不同的生产者需要对应不同的ConfirmCallback，
     * 如果rabbitTemplate设置为单例bean，
     * 则所有的rabbitTemplate实际的ConfirmCallback为最后一次申明的ConfirmCallback。
     * @return
     */
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        return template;
    }

}
