package com.luoyu.aop.service.impl;

import com.luoyu.aop.service.TestService;
import com.luoyu.aop.vo.request.TestRequest;
import com.luoyu.aop.vo.response.TestResponse;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/10 10:32 上午
 * @Version: 1.0.0
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public TestResponse get(TestRequest testRequest) throws Exception {
        TestResponse testResponse = new TestResponse();
        testResponse.setId(testRequest.getId());
        testResponse.setName(testRequest.getName());
        testResponse.setSex(testRequest.getSex());
        return testResponse;
    }

}
