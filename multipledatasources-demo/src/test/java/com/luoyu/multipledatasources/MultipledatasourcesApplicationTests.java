package com.luoyu.multipledatasources;

import com.luoyu.multipledatasources.entity.Task;
import com.luoyu.multipledatasources.entity.User;
import com.luoyu.multipledatasources.service.ITaskService;
import com.luoyu.multipledatasources.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class MultipledatasourcesApplicationTests {

    @Resource
    private IUserService iUserService;

    @Resource
    private ITaskService iTaskService;

    @Test
    void selectDb1Test() {
        // 验证多数据源db1
        log.info("使用自定义的方法，查看db1");
        User user = iUserService.selectByName("Jack");
        log.info(user.toString());
    }

    @Test
    void selectDb2Test() {
        // 验证多数据源db2
        log.info("使用自定义的方法，查看db2");
        Task task = iTaskService.selectByName("韩信");
        log.info(task.toString());
    }

    @Test
    void updateDb1Test() {
        // 验证事务
        iUserService.updateName(1, "改名啦");
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
