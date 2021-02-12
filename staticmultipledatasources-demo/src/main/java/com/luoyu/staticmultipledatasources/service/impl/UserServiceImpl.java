package com.luoyu.staticmultipledatasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyu.staticmultipledatasources.entity.User;
import com.luoyu.staticmultipledatasources.mapper.db1.UserMapper;
import com.luoyu.staticmultipledatasources.service.IUserService;
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
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
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
    @Transactional(value = "db1TransactionManger")
    @Override
    public int updateName(int id, String name) {
        int count = userMapper.updateName(id, name);
        int i = 1/0;
        return count;
    }

}
