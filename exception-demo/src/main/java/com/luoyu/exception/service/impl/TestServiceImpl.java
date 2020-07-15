package com.luoyu.exception.service.impl;


import com.luoyu.exception.service.TestService;
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
    public void get() throws Exception {
        int i = 1/0;
    }
}
