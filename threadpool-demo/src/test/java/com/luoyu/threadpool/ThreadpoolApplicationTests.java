package com.luoyu.threadpool;

import com.luoyu.threadpool.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class ThreadpoolApplicationTests {

    @Autowired
    private TestService testService;

    @Test
    void test2(){
        testService.test2();
    }

    @Test
    void test3(){
        testService.test3();
    }

    @Test
    void test4(){
        testService.test4();
    }

    @Test
    void test5() throws Exception {
        testService.test5();
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
