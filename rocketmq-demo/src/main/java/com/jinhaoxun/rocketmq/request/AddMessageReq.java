package com.jinhaoxun.rocketmq.request;

import com.jinhaoxun.rocketmq.message.Message;
import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 发送RocketMQ消息请求实体类
 */
@Setter
@Getter
public class AddMessageReq {

    private String topic;

    private String tag;

    private Message<String> message;

}
