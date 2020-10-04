package com.luoyu.thymeleaf.service.impl;

import com.luoyu.thymeleaf.entity.Test;
import com.luoyu.thymeleaf.service.iTestService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Author: luoyu
 * @Date: 2020/4/12 下午1:27
 * @Version: 1.0.0
 */
@Service
public class TestServiceImpl implements iTestService {

    /**
     * @Author: luoyu
     * @Description: 随机返回一组数据用于展示
     * @Date: 2020/5/10 4:43 下午
     * @Return: java.util.List<com.jinhaoxun.thymeleaf.pojo.Test>
     * @Throws:
     */
    @Override
    public List<Test> getTest() {
        List<Test> testList = new ArrayList<>();
        Random random = new Random();
        int count = random.nextInt(30);
        for (int i = 0; i < count; i++) {
            Test test = new Test();
            if(i%3 == 0){
                test.setId("123");
                test.setName("李白");
                test.setAge(18);
                test.setSex("男");
            }else if(i%3 == 1){
                test.setId("456");
                test.setName("韩信");
                test.setAge(20);
                test.setSex("男");
            }else {
                test.setId("789");
                test.setName("露娜");
                test.setAge(16);
                test.setSex("女");
            }
            testList.add(test);
        }
        return testList;
    }

}
