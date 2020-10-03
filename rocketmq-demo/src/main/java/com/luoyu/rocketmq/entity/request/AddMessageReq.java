package com.luoyu.rocketmq.entity.request;

import com.luoyu.rocketmq.entity.Message;
import lombok.Data;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description 发送RocketMQ消息请求实体类
 */
@Data
public class AddMessageReq {

    private String topic;

    private String tag;

    private Message<String> message;

}
