package com.luoyu.redis.service.impl;

import com.luoyu.redis.entity.Test;
import com.luoyu.redis.service.TestService;
import com.luoyu.redis.util.JsonUtils;
import com.luoyu.redis.util.MapUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Service
public class TestServiceImpl implements TestService {
    
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String PIPELINED_KEY = "pipelined:";

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

    @Override
    public List<Test> gets() {
        // 获取指定key的所有hashKey
        Set<String> testSet = redisTemplate.keys(PIPELINED_KEY + "*");
        if (CollectionUtils.isEmpty(testSet)) {
            return Collections.emptyList();
        }
        // 根据hashKey依次获取所有value
        List<Map> mapList = redisTemplate.executePipelined((RedisCallback<Map>) connection -> {
            if (!CollectionUtils.isEmpty(testSet)) {
                for (String item : testSet) {
                    connection.get((item).getBytes());
                }
            }
            return null;
        });

        List<Test> testList = new ArrayList<>();
        mapList.forEach(mapListItem -> {
            Test test = MapUtils.mapToObject(mapListItem, Test.class);
            testList.add(test);
        });

        return testList;
    }

    @Override
    public void sets(List<Test> tests) {
        redisTemplate.executePipelined((RedisCallback) connection -> {
            if (!CollectionUtils.isEmpty(tests)) {
                for (Test item : tests) {
                    connection.set((PIPELINED_KEY + item.getId()).getBytes(), JsonUtils.objectToJson(item).getBytes(), Expiration.seconds(1000), RedisStringCommands.SetOption.UPSERT);
                }
            }
            return null;
        });
    }

    @Override
    public void deletes() {
        // 获取指定key的所有hashKey
        Set<String> testSet = redisTemplate.keys(PIPELINED_KEY + "*");
        if (CollectionUtils.isEmpty(testSet)) {
            return;
        }
        redisTemplate.executePipelined((RedisCallback) connection -> {
            if (!CollectionUtils.isEmpty(testSet)) {
                for (String item : testSet) {
                    connection.del((item).getBytes());
                }
            }
            return null;
        });
    }

}
