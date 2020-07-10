package com.luoyu.shiro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/4/11 下午8:17
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/getlist")
    public String index(){
        return null;
    }

}
