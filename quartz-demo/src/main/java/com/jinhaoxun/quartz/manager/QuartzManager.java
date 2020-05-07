package com.jinhaoxun.quartz.manager;

import com.jinhaoxun.quartz.request.AddCronJobReq;
import com.jinhaoxun.quartz.request.AddSimpleJobReq;
import com.jinhaoxun.quartz.request.DeleteJobReq;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;

import static org.quartz.DateBuilder.futureDate;

/**
 * @Description: Quartz管理操作类
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Slf4j
@Service
public class QuartzManager {

    private Scheduler scheduler;

    /**
     * @author jinhaoxun
     * @description 构造器
     * @param scheduler 调度器
     */
    public QuartzManager(Scheduler scheduler){
        this.scheduler = scheduler;
    }

    /**
     * quartz任务类包路径
     */
    private static String jobUri = "com.jinhaoxun.quartz.job.";

    /**
     * @author jinhaoxun
     * @description 添加一个Simple定时任务，只执行一次的定时任务
     * @param addSimpleJobReq 参数对象
     * @param taskId 任务ID，不能同名
     * @throws RuntimeException
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addSimpleJob(AddSimpleJobReq addSimpleJobReq, String taskId) throws Exception {
        String jobUrl = jobUri + addSimpleJobReq.getJobClass();
        try {
            Class<? extends Job> aClass = (Class<? extends Job>) Class.forName(jobUrl).newInstance().getClass();
            // 任务名，任务组，任务执行类
            JobDetail job = JobBuilder.newJob(aClass).withIdentity(taskId,
                    "JobGroup").build();
            //增加任务ID参数
            addSimpleJobReq.getParams().put("taskId",taskId);
            // 添加任务参数
            job.getJobDataMap().putAll(addSimpleJobReq.getParams());
            // 转换为时间差，秒单位
            int time = (int) (addSimpleJobReq.getDate().getTime() - System.currentTimeMillis()) / 1000;

            SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                    .withIdentity(taskId, taskId + "TiggerGroup")
                    .startAt(futureDate(time, DateBuilder.IntervalUnit.SECOND))
                    .build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(job, trigger);
            if (!scheduler.isShutdown()) {
                // 启动
                scheduler.start();
            }
        } catch (Exception e) {
            log.info("Quartz新增任务失败");
        }
    }

    /**
     * @author jinhaoxun
     * @description 添加一个Cron定时任务，循环不断执行的定时任务
     * @param addCronJobReq 参数对象
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addCronJob(AddCronJobReq addCronJobReq) throws Exception {
        String jobUrl = jobUri + addCronJobReq.getJobClass();
        try {
            Class<? extends Job> aClass = (Class<? extends Job>) Class.forName(jobUrl).newInstance().getClass();
            // 任务名，任务组，任务执行类
            JobDetail job = JobBuilder.newJob(aClass).withIdentity(addCronJobReq.getJobName(),
                    addCronJobReq.getJobGroupName()).build();
            // 添加任务参数
            job.getJobDataMap().putAll(addCronJobReq.getParams());
            // 创建触发器
            CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
                    // 触发器名,触发器组
                    .withIdentity(addCronJobReq.getTriggerName(), addCronJobReq.getTriggerGroupName())
                    // 触发器时间设定
                    .withSchedule(CronScheduleBuilder.cronSchedule(addCronJobReq.getDate()))
                    .build();
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(job, trigger);

            if (!scheduler.isShutdown()) {
                // 启动
                scheduler.start();
            }
        } catch (Exception e) {
            log.info("Quartz新增任务失败");
        }
    }

    /**
     * @author jinhaoxun
     * @description 修改一个任务的触发时间
     * @param triggerName       触发器名
     * @param triggerGroupName  触发器组名
     * @param cron              时间设置，参考quartz说明文档
     * @throws Exception
     */
    public void modifyJobTime(String triggerName, String triggerGroupName, String cron) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);
            }
        } catch (Exception e) {
            log.info("Quartz修改任务失败");
        }
    }
    /**
     * @author jinhaoxun
     * @description 移除一个任务
     * @param deleteJobReq     参数对象
     * @throws Exception
     */
    public void removeJob(DeleteJobReq deleteJobReq) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(deleteJobReq.getTriggerName(), deleteJobReq.getTriggerGroupName());
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(deleteJobReq.getJobName(), deleteJobReq.getJobGroupName()));
        } catch (Exception e) {
            log.info("Quartz删除改任务失败");
        }
    }

    /**
     * @author jinhaoxun
     * @description 获取任务是否存在
     * @param triggerName       触发器名
     * @param triggerGroupName  触发器组名
     * @return Boolean 返回操作结果
     * 获取任务是否存在
     * STATE_BLOCKED 4 阻塞
     * STATE_COMPLETE 2 完成
     * STATE_ERROR 3 错误
     * STATE_NONE -1 不存在
     * STATE_NORMAL 0 正常
     * STATE_PAUSED 1 暂停
     * @throws Exception
     */
    public Boolean notExists(String triggerName, String triggerGroupName) throws Exception {
        try {
            if (scheduler.getTriggerState(TriggerKey.triggerKey(triggerName, triggerGroupName)) == Trigger.TriggerState.NORMAL){
                return true;
            }
        } catch (Exception e) {
            log.info("Quartz获取任务是否存在失败");
        }
        return false;
    }

    /**
     * @author jinhaoxun
     * @description 关闭调度器
     * @throws RuntimeException
     */
    public void shutdown() throws Exception {
        try {
            if(scheduler.isStarted()){
                scheduler.shutdown(true);
            }
        } catch (Exception e) {
            log.info("Quartz关闭调度器失败");
        }
    }

}