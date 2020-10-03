package com.luoyu.kafka.service.impl;

import com.luoyu.kafka.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class KafkaServiceImpl implements KafkaService {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public boolean sendMessage(String message) {
        // 发送消息
        kafkaTemplate.send("test_topic", message);
        return true;
    }

}
