package com.jinhaoxun.mpmultipledatasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinhaoxun.mpmultipledatasources.entity.Test;
import com.jinhaoxun.mpmultipledatasources.entity.User;
import com.jinhaoxun.mpmultipledatasources.mapper.db1.TestMapper;
import com.jinhaoxun.mpmultipledatasources.mapper.db2.UserMapper;
import com.jinhaoxun.mpmultipledatasources.service.IUserService;
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
public class UserServiceImpl extends ServiceImpl<TestMapper, User> implements IUserService {

    @Resource
    private TestMapper testMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.mybatisplusdemo.entity.Test
     * @Throws:
     */
    @Override
    public Test selectTestByName(String name) {
        return testMapper.selectByName(name);
    }

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.mybatisplusdemo.entity.User
     * @Throws:
     */
    @Override
    public User selectUserByName(String name) {
        return userMapper.selectByName(name);
    }
}
