package com.luoyu.dynamicmultipledatasources.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luoyu.dynamicmultipledatasources.aop.ChangeDataSource;
import com.luoyu.dynamicmultipledatasources.entity.Task;
import com.luoyu.dynamicmultipledatasources.mapper.TaskMapper;
import com.luoyu.dynamicmultipledatasources.service.ITaskService;
import com.luoyu.dynamicmultipledatasources.util.SpringUtils;
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
     * @Description: 解决内部方法调用AOP失效问题
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    @Override
    public Task selectByInside(Integer id) {
        return this.getProxy().selectById(id);
    }

    /**
     * @Author: jinhaoxun
     * @Description: 从spring容器里手动拿到AOP代理类，解决AOP失效问题
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    private TaskServiceImpl getProxy(){
        return SpringUtils.getBean(this.getClass());
    }

    /**
     * @Author: jinhaoxun
     * @Description:
     * @param id id
     * @Date: 2020/2/13 下午12:06
     * @Throws:
     */
    @ChangeDataSource("db2")
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
    @Transactional(rollbackFor = Exception.class)
    @ChangeDataSource("db2")
    @Override
    public int updateName(int id, String name) {
        int count = taskMapper.updateName(id, name);
        int i = 1/0;
        return count;
    }

}
