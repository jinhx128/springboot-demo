package com.luoyu.thymeleaf.service;

import com.luoyu.thymeleaf.entity.Test;

import java.util.List;

/**
 * @Description:
 * @Author: luoyu
 * @Date: 2020/4/12 下午1:23
 * @Version: 1.0.0
 */
public interface iTestService {

    /**
     * @Author: jinhaoxun
     * @Description: 随机返回一组数据用于展示
     * @Date: 2020/5/10 4:43 下午
     * @Return: java.util.List<com.jinhaoxun.thymeleaf.pojo.Test>
     * @Throws:
     */
    List<Test> getTest();

}
