package com.luoyu.exception.service.impl;

import com.luoyu.exception.exception.CustomException;
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
    public void get1() throws Exception {
        int i = 1/0;
    }

    @Override
    public void get2() throws Exception {
        try {
            int i = 1/0;
        }catch (Exception e){
            throw new CustomException(10086, "自定义打印异常", "自定义返回异常");
        }
    }

}
