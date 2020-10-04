package com.luoyu.thymeleaf.controller;

import com.luoyu.thymeleaf.entity.Test;
import com.luoyu.thymeleaf.service.iTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * @Description:
 * @Author: luoyu
 * @Date: 2020/4/11 下午8:17
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private iTestService iTestService;

    @GetMapping("/getlist")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        List<Test> testList = iTestService.getTest();
        mv.addObject("list", testList);
        mv.addObject("model", "测试一下模块名");
        mv.setViewName("/index.html");
        return mv;
    }

}
