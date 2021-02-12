package com.luoyu.staticmultipledatasources.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luoyu.staticmultipledatasources.entity.Task;

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
