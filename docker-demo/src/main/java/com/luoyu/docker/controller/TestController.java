package com.luoyu.docker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: luoyu
 * @Date: 2020/7/16 10:39 下午
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * @author jinhaoxun
     * @description 测试接口
     */
    @GetMapping(value = "/get", produces = "application/json; charset=UTF-8")
    public String getTest1() throws Exception {
        return "Hello";
    }
}
