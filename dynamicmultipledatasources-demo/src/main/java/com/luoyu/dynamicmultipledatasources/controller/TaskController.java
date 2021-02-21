package com.luoyu.dynamicmultipledatasources.controller;

import com.luoyu.dynamicmultipledatasources.entity.Task;
import com.luoyu.dynamicmultipledatasources.service.ITaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
@RestController
public class TaskController {

    @Autowired
    private ITaskService iTaskService;

    /**
     * 查询db2
     */
    @GetMapping(value = "/task")
    public Task getTaskById(@RequestParam("id") Integer id) {
        return iTaskService.selectById(id);
    }

    /**
     * 测试事物
     */
    @PutMapping(value = "/task")
    public int updateName(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        return iTaskService.updateName(id, name);

    }

    /**
     * 测试解决内部方法调用AOP失效问题
     */
    @GetMapping(value = "/taskByInside")
    public Task getTaskByInside(@RequestParam("id") Integer id) {
        return iTaskService.selectByInside(id);
    }

}
