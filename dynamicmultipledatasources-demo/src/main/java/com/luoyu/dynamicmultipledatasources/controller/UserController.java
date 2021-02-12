package com.luoyu.dynamicmultipledatasources.controller;

import com.luoyu.dynamicmultipledatasources.entity.User;
import com.luoyu.dynamicmultipledatasources.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author luoyu
 * @since 2020-02-13
 */
@RestController
public class UserController {

    @Autowired
    private IUserService iUserService;

    /**
     * 查询db1
     */
    @GetMapping(value = "/user")
    public User getUserById(@RequestParam("id") Integer id) {
        return iUserService.selectById(id);
    }

    /**
     * 测试事物
     */
    @PutMapping(value = "/user")
    public int updateName(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        return iUserService.updateName(id, name);

    }

}
