package com.luoyu.redis.service.impl;

import com.luoyu.redis.entity.Test;
import com.luoyu.redis.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Override
    public Test get(String id) {
        log.info("没有使用缓存，创建一个新对象返回，并设置到缓存");

        Test test = new Test();
        test.setId(id);
        test.setName("测试name");
        test.setAge(12);
        test.setSex("男");

        return test;
    }

    @Override
    public boolean add(Test test) {
        log.info("创建id为：" + test.getId() + "的对象存起来，并设置到缓存，对象为：" + test.toString());

        return true;
    }

    @Override
    public boolean delete(String id) {
        log.info("删除id为：" + id + "的对象，并从缓存中删除");

        return true;
    }

    @Override
    public boolean update(Test test) {
        log.info("更新id为：" + test.getId() + "的对象，并更新到缓存，新对象为：" + test.toString());

        return true;
    }

}
