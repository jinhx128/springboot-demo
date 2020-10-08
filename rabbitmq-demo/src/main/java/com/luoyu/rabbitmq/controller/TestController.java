package com.luoyu.rabbitmq.controller;

import com.luoyu.rabbitmq.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author :jhx
 * @date :2020/12/26
 * @desc :
 */
@RestController
@Slf4j
@RequestMapping(value = "/message")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 发送消息test1
     * @param content
     * @return
     */
    @PostMapping(value = "/test1")
    public String sendTest1(@RequestBody String content) {
        return testService.sendTest1(content);
    }

    /**
     * 发送消息test2
     * @param content
     * @return
     */
    @PostMapping(value = "/test2")
    public String sendTest2(@RequestBody String content) {
        return testService.sendTest2(content);
    }

}
