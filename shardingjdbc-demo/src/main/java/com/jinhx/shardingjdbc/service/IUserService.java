package com.jinhx.shardingjdbc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinhx.shardingjdbc.entity.User;

/**
 * IUserService
 *
 * @author jinhx
 * @date 2021-07-27
 */
public interface IUserService extends IService<User> {

    /**
     * @param age age
     * @return User
     */
    User selectByAge(Long age);

    /**
     * @param id id
     * @return User
     */
    User selectById(Long id);

    /**
     * @param user user
     * @return Boolean
     */
    Boolean insertUser(User user);

}
