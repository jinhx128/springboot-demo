package com.jinhaoxun.thymeleaf.controller;

import com.jinhaoxun.thymeleaf.pojo.Test;
import com.jinhaoxun.thymeleaf.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/4/11 下午8:17
 * @Version: 1.0.0
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("/getlist")
    public ModelAndView index(){
        ModelAndView mv = new ModelAndView();
        List<Test> testList = testService.getTest();
        mv.addObject("list", testList);
        mv.addObject("model", "测试一下模块名");
        mv.setViewName("/index.html");
        return mv;
    }

}
