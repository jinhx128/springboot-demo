package com.jinhaoxun.multipledatasources.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jinhaoxun.multipledatasources.entity.Task;

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
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.multipledatasources.entity.Task
     * @Throws:
     */
    Task selectByName(String name);

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
