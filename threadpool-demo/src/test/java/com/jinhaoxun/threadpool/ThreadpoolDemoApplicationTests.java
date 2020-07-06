package com.jinhaoxun.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class ThreadpoolDemoApplicationTests {

    @Resource(name = "testTaskExecutor1")
    private ThreadPoolTaskExecutor testTaskExecutor1;
    @Resource(name = "testTaskExecutor2")
    private ThreadPoolTaskExecutor testTaskExecutor2;

    @Async
    @Test
    void test1(){
        testTaskExecutor1.execute(() -> {
            log.info("看看是哪个线程执行了我！");
        });
    }

    @Test
    void test2(){
        testTaskExecutor1.execute(() -> {
            log.info("看看是哪个线程执行了我！");
        });
    }

    @Async
    @Test
    void test3(){
        testTaskExecutor2.execute(() -> {
            log.info("看看是哪个线程执行了我！");
        });
    }

    @Test
    void test4(){
        testTaskExecutor2.execute(() -> {
            log.info("看看是哪个线程执行了我！");
        });
    }

    @BeforeEach
    void testBefore(){
        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter(){
        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
