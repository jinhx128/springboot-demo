package com.luoyu.aop.service;

import com.luoyu.aop.vo.http.HttpResponse;
import com.luoyu.aop.vo.request.TestRequest;
import com.luoyu.aop.vo.response.TestResponse;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/10 10:31 上午
 * @Version: 1.0.0
 */
public interface TestService {

    TestResponse get(TestRequest testRequest) throws Exception;

}
