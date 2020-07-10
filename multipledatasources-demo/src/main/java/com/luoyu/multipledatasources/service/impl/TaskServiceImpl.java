package com.luoyu.multipledatasources.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyu.multipledatasources.entity.Task;
import com.luoyu.multipledatasources.mapper.db2.TaskMapper;
import com.luoyu.multipledatasources.service.ITaskService;
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
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Resource
    private TaskMapper taskMapper;

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.multipledatasources.entity.Task
     * @Throws:
     */
    @Override
    public Task selectByName(String name) {
        return taskMapper.selectByName(name);
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
    @Transactional(value = "twoTransactionManger")
    @Override
    public int updateName(int id, String name) {
        taskMapper.updateName(id, name);
        int i = 1/0;
        return 0;
    }
}
