package com.luoyu.rocketmq.service;

import com.luoyu.rocketmq.entity.request.AddMessageReq;

public interface RocketMQService {

    boolean sendMessage(AddMessageReq addMessageReq);

    boolean syncSendMessage(AddMessageReq addMessageReq);

}
