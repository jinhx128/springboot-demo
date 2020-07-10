package com.luoyu.multipledatasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyu.multipledatasources.entity.User;
import com.luoyu.multipledatasources.mapper.db1.UserMapper;
import com.luoyu.multipledatasources.service.IUserService;
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
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.multipledatasources.entity.User
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
    @Transactional(value = "oneTransactionManger")
    @Override
    public int updateName(int id, String name) {
        userMapper.updateName(id, name);
        int i = 1/0;
        return 0;
    }
}
