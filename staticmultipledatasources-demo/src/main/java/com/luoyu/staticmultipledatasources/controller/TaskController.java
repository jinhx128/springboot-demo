package com.luoyu.staticmultipledatasources.controller;

import com.luoyu.staticmultipledatasources.entity.Task;
import com.luoyu.staticmultipledatasources.service.ITaskService;
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

}
