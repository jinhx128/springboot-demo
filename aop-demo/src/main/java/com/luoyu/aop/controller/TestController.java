package com.luoyu.aop.controller;

import com.luoyu.aop.aop.HttpCheck;
import com.luoyu.aop.service.TestService;
import com.luoyu.aop.entity.http.HttpRequest;
import com.luoyu.aop.entity.http.HttpResponse;
import com.luoyu.aop.entity.request.TestRequest;
import com.luoyu.aop.entity.response.TestResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description:
 * @Author: luoyu
 * @Date: 2020/7/10 10:31 上午
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    TestService testService;

    /**
     * @author luoyu
     * @description 测试接口1
     */
    @PostMapping(value = "/test1", produces = "application/json; charset=UTF-8")
    @HttpCheck(dataType = TestRequest.class)
    public HttpResponse<TestResponse> Test1(@RequestBody HttpRequest<TestRequest> httpRequest) throws Exception {
        TestResponse testResponse = testService.get(httpRequest.getData());
        return HttpResponse.build(200, "成功！", testResponse);
    }

    /**
     * @author luoyu
     * @description 测试接口2
     */
    @PostMapping(value = "/test2", produces = "application/json; charset=UTF-8")
    @HttpCheck(dataType = TestRequest.class, isTimeout = false)
    public HttpResponse<TestResponse> Test2(@RequestBody HttpRequest<TestRequest> httpRequest) throws Exception {
        TestResponse testResponse = testService.get(httpRequest.getData());
        return HttpResponse.build(200, "成功！", testResponse);
    }

    /**
     * @author luoyu
     * @description 测试接口3
     */
    @PostMapping(value = "/test3", produces = "application/json; charset=UTF-8")
    @HttpCheck(dataType = TestRequest.class, isDecrypt = false, isTimeout = false)
    public HttpResponse<TestResponse> Test3(@RequestBody HttpRequest<TestRequest> httpRequest) throws Exception {
        TestResponse testResponse = testService.get(httpRequest.getData());
        return HttpResponse.build(200, "成功！", testResponse);
    }

    /**
     * @author luoyu
     * @description 测试接口4
     */
    @PostMapping(value = "/test4", produces = "application/json; charset=UTF-8")
    @HttpCheck(dataType = TestRequest.class, isEncrypt = false, isTimeout = false)
    public HttpResponse<TestResponse> Test4(@RequestBody HttpRequest<TestRequest> httpRequest) throws Exception {
        TestResponse testResponse = testService.get(httpRequest.getData());
        return HttpResponse.build(200, "成功！", testResponse);
    }

    /**
     * @author luoyu
     * @description 测试接口5
     */
    @PostMapping(value = "/test5", produces = "application/json; charset=UTF-8")
    @HttpCheck(dataType = TestRequest.class, isDecrypt = false, isEncrypt = false, isTimeout = false)
    public HttpResponse<TestResponse> Test5(@RequestBody HttpRequest<TestRequest> httpRequest) throws Exception {
        TestResponse testResponse = testService.get(httpRequest.getData());
        return HttpResponse.build(200, "成功！", testResponse);
    }

}
