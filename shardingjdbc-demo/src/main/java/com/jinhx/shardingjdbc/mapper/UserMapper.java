package com.jinhx.shardingjdbc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jinhx.shardingjdbc.entity.User;

/**
 * UserMapper
 *
 * @author jinhx
 * @date 2021-07-27
 */
public interface UserMapper extends BaseMapper<User> {

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
     * @return Integer
     */
    Integer insertUser(User user);

}
