package com.jinhx.shardingjdbc.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * 雪花算法工具类
 *
 * @author jinhx
 * @since 2021/7/24
 */
@Slf4j
public class SnowFlakeUtil {

    /**
     * 机器位，5位，即范围0-31
     */
    private static long workerId = 0;

    /**
     * 机器位最大个数，即范围0-31，一定是2的n次方
     */
    private static final int WORKER_ID_COUNT = 32;

    /**
     * 表位，5位，即范围0-31
     */
    private static long dataCenterId = 0;

    /**
     * 表位最大个数，即范围0-31，一定是2的n次方
     */
    public static final int DATA_CENTER_ID_COUNT = 32;

    /**
     * 默认对象
     */
    private static Snowflake snowflake = IdUtil.getSnowflake(workerId,dataCenterId);

    /**
     * 启动服务的时候进行初始化，获取机器workId
     */
    @PostConstruct
    public void init() {
        try {
            workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) & (WORKER_ID_COUNT - 1);
            log.info("通过ip获取机器的workId成功: {}", workerId);
        } catch (Exception e) {
            e.getStackTrace();
            log.error("通过ip获取机器的workId失败：", e);
            workerId = NetUtil.getLocalhostStr().hashCode() & (WORKER_ID_COUNT - 1);
            log.info("通过ip进行哈希获取机器的workId成功: {}", workerId);
        }
    }

    /**
     * 通过默认对象获取雪花算法id
     *
     * @return 雪花算法id
     */
    public static long getSnowflakeId() {
        return snowflake.nextId();
    }

    /**
     * 通过指定workerId，dataCenterId获取雪花算法id
     *
     * @param workerId workerId
     * @param dataCenterId dataCenterId
     * @return 雪花算法id
     */
    public static long getSnowflakeId(long workerId, long dataCenterId) {
        if (workerId >= WORKER_ID_COUNT){
            throw new RuntimeException("机器位值" + workerId + "超过限制" + WORKER_ID_COUNT);
        }
        if (dataCenterId >= DATA_CENTER_ID_COUNT){
            throw new RuntimeException("表位值" + dataCenterId + "超过限制" + DATA_CENTER_ID_COUNT);
        }
        Snowflake snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
        return snowflake.nextId();
    }

    /**
     * 通过指定dataCenterId获取雪花算法id
     *
     * @param dataCenterId dataCenterId
     * @return 雪花算法id
     */
    public static long getSnowflakeId(long dataCenterId) {
        if (dataCenterId >= DATA_CENTER_ID_COUNT){
            throw new RuntimeException("表位值" + dataCenterId + "超过限制" + DATA_CENTER_ID_COUNT);
        }
        Snowflake snowflake = IdUtil.getSnowflake(workerId, dataCenterId);
        return snowflake.nextId();
    }

    /**
     * 通过id获取dataCenterId
     *
     * @param id id
     * @return dataCenterId
     */
    public static long getDataCenterId(long id) {
        return snowflake.getDataCenterId(id);
    }

//    public static void main(String[] args) {
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        Stream.iterate(0,x->x+1).
//                limit(20).
//                forEach(x->{
//                    executorService.submit(()->{
//                        long id = SnowFlakeUtil.getSnowflakeId();
//                        System.out.println(id);
//                    });
//                });
//        executorService.shutdown();
//    }

}
