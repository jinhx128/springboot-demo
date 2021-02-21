package com.luoyu.dynamicmultipledatasources.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luoyu.dynamicmultipledatasources.entity.Task;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jinhaoxun
 * @since 2020-02-13
 */
public interface ITaskService extends IService<Task> {

    /**
     * @Author: jinhaoxun
     * @Description: 解决内部方法调用AOP失效问题
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    Task selectByInside(Integer id);

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    Task selectById(Integer id);

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param id id
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: int
     * @Throws:
     */
    int updateName(int id, String name);

}
