package com.jinhx.shardingjdbc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinhx.shardingjdbc.entity.User;
import com.jinhx.shardingjdbc.mapper.UserMapper;
import com.jinhx.shardingjdbc.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserServiceImpl
 *
 * @author jinhx
 * @date 2021-07-27
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * @param age age
     * @return User
     */
    @Override
    public User selectByAge(Long age) {
        return userMapper.selectByAge(age);
    }

    /**
     * @param id id
     * @return User
     */
    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }

    /**
     * @param user user
     * @return Boolean
     */
    @Override
    public Boolean insertUser(User user) {
        return userMapper.insertUser(user) > 0;
    }

}
