package com.jinhaoxun.quartzdemo.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

/**
 * @version: 1.0
 * @author jinhaoxun
 * @date 2020-01-09
 * @description Job测试类
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
