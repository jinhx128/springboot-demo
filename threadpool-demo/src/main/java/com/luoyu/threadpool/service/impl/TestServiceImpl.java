package com.luoyu.threadpool.service.impl;

import com.luoyu.threadpool.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TestServiceImpl implements TestService {

    @Resource(name = "testTaskExecutor")
    private ThreadPoolTaskExecutor testTaskExecutor;

    // 定时任务，一秒执行一次
    @Scheduled(fixedRate  = 1000)
    @Override
    public void test1() {
        log.info("定时任务，一秒执行一次，看看是哪个线程执行了我！{}", Thread.currentThread().getName());
    }

    @Override
    public void test2() {
        log.info("看看是哪个线程执行了我！{}", Thread.currentThread().getName());
    }

    @Override
    public void test3() {
        for (int i = 0; i < 10; i++) {
            testTaskExecutor.execute(() -> {
                log.info("看看是哪个线程执行了我！{}", Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @Async("testTaskExecutor")
    @Override
    public void test4() {
        log.info("看看是哪个线程执行了我！{}", Thread.currentThread().getName());
    }

}
