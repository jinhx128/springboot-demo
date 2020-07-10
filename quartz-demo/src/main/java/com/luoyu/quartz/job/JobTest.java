package com.luoyu.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * @Description: Job测试类
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Slf4j
public class JobTest implements Job {

    /**
     * @author jinhaoxun
     * @description 重写任务内容
     * @param jobExecutionContext 设置的key
     * @throws
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        //获取参数
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String id = dataMap.getString("id");
        String name = dataMap.getString("name");
        try {
            log.info("执行任务{},{}",id,name);
        } catch (Exception e) {
            log.info("Quartz执行失败");
        }
    }
}
