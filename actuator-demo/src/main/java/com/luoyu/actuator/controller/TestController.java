package com.luoyu.actuator.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author luoyu
 * @since 2020-02-13
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    /**
     * @author luoyu
     * @description 测试接口
     */
    @GetMapping(value = "/get", produces = "application/json; charset=UTF-8")
    public String getTest1() throws Exception {
        log.info("开始");
        Thread.sleep(10000L);
        log.info("结束");
        return "请求成功！";
    }

}
