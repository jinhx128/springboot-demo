package com.luoyu.redis.controller;

import com.luoyu.redis.entity.Test;
import com.luoyu.redis.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 查询一个对象并返回
     *
     * @return
     */
    @GetMapping("/get")
    @Cacheable(value = "redis.test.key:", key = "#id")
    public Test get(@RequestParam(required = true) String id) {
        log.info("查询一个对象并返回");
        return testService.get(id);
    }

    /**
     * 删除一个对象
     *
     * @return
     */
    @DeleteMapping("/delete")
    @CacheEvict(value = "redis.test.key:", key = "#id")
    public boolean delete(@RequestParam(required = true) String id) {
        log.info("删除一个对象");
        return testService.delete(id);
    }

    /**
     * 新增一个对象
     *
     * @return
     */
    @PostMapping("/add")
    @Cacheable(value = "redis.test.key:", key = "#test.getId()")
    public Test add(@RequestBody Test test) {
        log.info("新增一个对象，结果：" + testService.add(test));
        return test;
    }

    /**
     * 更新一个对象
     *
     * @return
     */
    @PutMapping("/update")
    @CachePut(value = "redis.test.key:", key = "#test.getId()")
    public Test update(@RequestBody Test test) {
        log.info("更新一个对象，结果：" + testService.update(test));
        return test;
    }

}
