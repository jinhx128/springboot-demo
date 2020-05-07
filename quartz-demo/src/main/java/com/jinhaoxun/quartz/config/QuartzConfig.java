package com.jinhaoxun.quartz.config;


import com.jinhaoxun.quartz.job.JobFactory;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Description: Quartz配置
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Slf4j
@Configuration
public class QuartzConfig {

    private JobFactory jobFactory;

    /**
     * @author jinhaoxun
     * @description 构造器
     * @param jobFactory
     */
    public QuartzConfig(JobFactory jobFactory){
        this.jobFactory = jobFactory;
    }

    /**
     * @author  jinhaoxun
     * @description 配置SchedulerFactoryBean，将一个方法产生为Bean并交给Spring容器管理
     * @return SchedulerFactoryBean
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        log.info("开始注入Quartz调度器工厂...");
        // Spring提供SchedulerFactoryBean为Scheduler提供配置信息,并被Spring容器管理其生命周期
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 设置自定义Job Factory，用于Spring管理Job bean
        factory.setJobFactory(jobFactory);
        log.info("注入Quartz调度器工厂成功！");
        return factory;
    }

    @Bean(name = "scheduler")
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
}