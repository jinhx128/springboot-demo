package com.luoyu.staticmultipledatasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyu.staticmultipledatasources.entity.Task;
import com.luoyu.staticmultipledatasources.mapper.db2.TaskMapper;
import com.luoyu.staticmultipledatasources.service.ITaskService;
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
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    @Override
    public Task selectById(Integer id) {
        return taskMapper.selectById(id);
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
    @Transactional(value = "db2TransactionManger")
    @Override
    public int updateName(int id, String name) {
        int count = taskMapper.updateName(id, name);
        int i = 1/0;
        return count;
    }

}
