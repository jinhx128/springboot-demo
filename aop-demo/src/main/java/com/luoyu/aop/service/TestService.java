package com.luoyu.aop.service;

import com.luoyu.aop.entity.request.TestRequest;
import com.luoyu.aop.entity.response.TestResponse;

/**
 * @Description:
 * @Author: luoyu
 * @Date: 2020/7/10 10:31 上午
 * @Version: 1.0.0
 */
public interface TestService {

    TestResponse get(TestRequest testRequest) throws Exception;

}
