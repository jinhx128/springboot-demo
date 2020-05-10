package com.jinhaoxun.mybatisplus.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jinhaoxun.mybatisplus.entity.Task;
import com.jinhaoxun.mybatisplus.entity.User;
import com.jinhaoxun.mybatisplus.mapper.TaskMapper;
import com.jinhaoxun.mybatisplus.mapper.UserMapper;
import com.jinhaoxun.mybatisplus.service.ITaskService;
import com.jinhaoxun.mybatisplus.service.IUserService;
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
@DS("db2")
@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements ITaskService {

    @Resource
    private TaskMapper taskMapper;

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param name 姓名
     * @Date: 2020/2/13 下午12:06
     * @Return: com.jinhaoxun.mybatisplus.entity.Task
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
    @Transactional
    @Override
    public int updateName(int id, String name) {
        return taskMapper.updateName(id, name);
    }
}
