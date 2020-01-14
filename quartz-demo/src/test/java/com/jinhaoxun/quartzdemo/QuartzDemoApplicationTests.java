package com.jinhaoxun.quartzdemo;

import com.jinhaoxun.quartzdemo.manager.QuartzManager;
import com.jinhaoxun.quartzdemo.request.AddCronJobServiceReq;
import com.jinhaoxun.quartzdemo.request.AddSimpleJobServiceReq;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SpringBootTest
class QuartzDemoApplicationTests {

    @Resource
    private QuartzManager quartzManager;

    @Test
    void contextLoads() {
    }

    @Test
    void addSimpleJobTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id","测试id");
        params.put("name","测试name");

        Calendar beforeTime = Calendar.getInstance();
        // 5 秒之后的时间
        beforeTime.add(Calendar.SECOND, 5);
        Date beforeDate = beforeTime.getTime();

        AddSimpleJobServiceReq addSimpleJobServiceReq = new AddSimpleJobServiceReq();
        addSimpleJobServiceReq.setDate(beforeDate);
        addSimpleJobServiceReq.setJobClass("JobTest");
        addSimpleJobServiceReq.setParams(params);
        quartzManager.addSimpleJob(addSimpleJobServiceReq, "123");
        // 让主线程睡眠60秒
        Thread.currentThread().sleep(60000);
    }

    @Test
    void addCronJobTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id","测试id");
        params.put("name","测试name");
        AddCronJobServiceReq addCronJobServiceReq = new AddCronJobServiceReq();
        //每 5 秒执行一次
        addCronJobServiceReq.setDate("0/5 * * * * ?");
        addCronJobServiceReq.setJobClass("JobTest");
        addCronJobServiceReq.setJobGroupName("JobGroupName");
        addCronJobServiceReq.setJobName("JobName");
        addCronJobServiceReq.setParams(params);
        addCronJobServiceReq.setTriggerGroupName("triggerGroupName");
        addCronJobServiceReq.setTriggerName("triggerName");
        quartzManager.addCronJob(addCronJobServiceReq);
        // 让主线程睡眠60秒
        Thread.currentThread().sleep(60000);
    }

    @Test
    void removeJobTest() throws Exception {
        quartzManager.removeJob("jobName", "jobGroupName","triggerName", "triggerGroupName");
    }

    @Test
    void shutdownTest() throws Exception {
        quartzManager.shutdown();
    }

}
