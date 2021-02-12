package com.luoyu.staticmultipledatasources.controller;

import com.luoyu.staticmultipledatasources.entity.User;
import com.luoyu.staticmultipledatasources.service.IUserService;
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
