package com.luoyu.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyu.mybatisplus.entity.User;
import com.luoyu.mybatisplus.mapper.UserMapper;
import com.luoyu.mybatisplus.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
@DS("db1")
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.mybatisplus.entity.User
     * @Throws:
     */
    @Override
    public User selectByName(String name) {
        return userMapper.selectByName(name);
    }

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param id id
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: int
     * @Throws:
     */
    @Transactional
    @Override
    public int updateName(int id, String name) {
        int n = userMapper.updateName(id, name);
        // 此处报错事务回滚
        int i = 1/0;
        return n;
    }
}
