package com.jinhaoxun.mybatisplus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinhaoxun.mybatisplus.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
public interface IUserService extends IService<User> {

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.mybatisplusdemo.entity.User
     * @Throws:
     */
    User selectByName(String name);

}
