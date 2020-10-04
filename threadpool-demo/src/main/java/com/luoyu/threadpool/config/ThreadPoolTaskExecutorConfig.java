package com.luoyu.threadpool.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @version 1.0
 * @author luoyu
 * @date 2019-08-09
 * @description 线程池配置
 */
@Configuration
@EnableAsync
@EnableScheduling
public class ThreadPoolTaskExecutorConfig {

    /**
     * 核心线程数（默认线程数）
     */
    @Value("${threadPoolTaskExecutor.corePoolSize}")
    private int corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${threadPoolTaskExecutor.maxPoolSize}")
    private int maxPoolSize;

    /**
     * 允许线程空闲时间（单位：默认为秒）
     */
    @Value("${threadPoolTaskExecutor.keepAliveTime}")
    private int keepAliveTime;

    /**
     * 缓冲队列数
     */
    @Value("${threadPoolTaskExecutor.queueCapacity}")
    private int queueCapacity;

    /**
     * 线程池名前缀
     */
    @Value("${threadPoolTaskExecutor.threadNamePrefix}")
    private String threadNamePrefix;

    /**
     * @return ThreadPoolTaskExecutor
     * @author jinhaoxun
     * @description 线程池配置，bean的名称，默认为首字母小写的方法名taskExecutor
     */
    @Bean("testTaskExecutor")
    public ThreadPoolTaskExecutor taskExecutor1() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        executor.setCorePoolSize(corePoolSize);
        //设置最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //线程池所使用的缓冲队列
        executor.setQueueCapacity(queueCapacity);
        //等待任务在关机时完成--表明等待所有线程执行完
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 等待时间 （默认为0，此时立即停止），并没等待xx秒后强制停止
        executor.setKeepAliveSeconds(keepAliveTime);
        // 线程名称前缀
        executor.setThreadNamePrefix(threadNamePrefix);
        // 线程池对拒绝任务的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return executor;
    }

}