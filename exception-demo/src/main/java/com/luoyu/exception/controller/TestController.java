package com.luoyu.exception.controller;

import com.luoyu.exception.entity.vo.Response;
import com.luoyu.exception.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/10 10:31 上午
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    /**
     * @author jinhaoxun
     * @description 测试接口1
     */
    @GetMapping("/get1")
    public Response get1() throws Exception {
        testService.get1();
        return Response.success();
    }

    /**
     * @author jinhaoxun
     * @description 测试接口2
     */
    @GetMapping("/get2")
    public Response get2() throws Exception {
        testService.get2();
        return Response.success();
    }

}
