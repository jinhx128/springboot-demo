package com.jinhaoxun.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinhaoxun.mybatisplus.entity.User;
import com.jinhaoxun.mybatisplus.mapper.UserMapper;
import com.jinhaoxun.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.mybatisplusdemo.entity.User
     * @Throws:
     */
    @Override
    public User selectByName(String name) {
        return userMapper.selectByName(name);
    }
}
