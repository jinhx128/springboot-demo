package com.luoyu.exception.controller;

import com.luoyu.exception.service.TestService;
import com.luoyu.exception.vo.http.HttpResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    TestService testService;

    /**
     * @author jinhaoxun
     * @description 测试接口1
     */
    @PostMapping(value = "/get1", produces = "application/json; charset=UTF-8")
    public HttpResponse get1() throws Exception {
        testService.get();
        return HttpResponse.build(200, "成功！", null);
    }

    /**
     * @author jinhaoxun
     * @description 测试接口2
     */
    @PostMapping(value = "/get2", produces = "application/json; charset=UTF-8")
    public HttpResponse get2() throws Exception {
        return HttpResponse.build(200, "成功！", null);
    }

}
