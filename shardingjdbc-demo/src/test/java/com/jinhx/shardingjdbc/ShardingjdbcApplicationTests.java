package com.jinhx.shardingjdbc;

import com.jinhx.shardingjdbc.entity.User;
import com.jinhx.shardingjdbc.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class ShardingjdbcApplicationTests {

    @Autowired
    private IUserService iUserService;

    @Test
    void selectByIdTest() {
        for (long i = 1;i < 100;i++){
            log.info(iUserService.selectById(i).toString());
        }
    }

    @Test
    void selectByIdAgeTest() {
        for (Long i = 1L;i < 100;i++){
            log.info(iUserService.selectByAge(i).toString());
        }
    }

    @Test
    void insertUserTest() {
        for (Long i = 1L;i < 100;i++){
            User user = new User();
            user.setAge(i);
            user.setName("name" + i);
            user.setEmail(i+ "@test.com");
            user.setId();
            iUserService.insertUser(user);
            log.info(user.toString());
        }
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
