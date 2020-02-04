package com.jinhaoxun.quartzdemo;

import com.jinhaoxun.quartzdemo.manager.QuartzManager;
import com.jinhaoxun.quartzdemo.request.AddCronJobReq;
import com.jinhaoxun.quartzdemo.request.AddSimpleJobReq;
import com.jinhaoxun.quartzdemo.request.DeleteJobReq;
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

        AddSimpleJobReq addSimpleJobReq = new AddSimpleJobReq();
        addSimpleJobReq.setDate(beforeDate);
        addSimpleJobReq.setJobClass("JobTest");
        addSimpleJobReq.setParams(params);
        quartzManager.addSimpleJob(addSimpleJobReq, "123");
        // 让主线程睡眠60秒
        Thread.currentThread().sleep(60000);
    }

    @Test
    void addCronJobTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id","测试id");
        params.put("name","测试name");
        AddCronJobReq addCronJobReq = new AddCronJobReq();
        //每 5 秒执行一次
        addCronJobReq.setDate("0/5 * * * * ?");
        addCronJobReq.setJobClass("JobTest");
        addCronJobReq.setJobGroupName("JobGroupName");
        addCronJobReq.setJobName("JobName");
        addCronJobReq.setParams(params);
        addCronJobReq.setTriggerGroupName("triggerGroupName");
        addCronJobReq.setTriggerName("triggerName");
        quartzManager.addCronJob(addCronJobReq);
        // 让主线程睡眠60秒
        Thread.currentThread().sleep(60000);
    }

    @Test
    void removeJobTest() throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("id","测试id");
        params.put("name","测试name");

        Calendar beforeTime = Calendar.getInstance();
        // 5 秒之后的时间
        beforeTime.add(Calendar.SECOND, 5);
        Date beforeDate = beforeTime.getTime();

        AddSimpleJobReq addSimpleJobReq = new AddSimpleJobReq();
        addSimpleJobReq.setDate(beforeDate);
        addSimpleJobReq.setJobClass("JobTest");
        addSimpleJobReq.setParams(params);
        quartzManager.addSimpleJob(addSimpleJobReq, "123");

        DeleteJobReq deleteJobReq = new DeleteJobReq();
        deleteJobReq.setJobName("123");
        deleteJobReq.setJobGroupName("123JobGroup");
        deleteJobReq.setTriggerName("123");
        deleteJobReq.setTriggerGroupName("123TiggerGroup");
        quartzManager.removeJob(deleteJobReq);
        // 让主线程睡眠60秒
        Thread.currentThread().sleep(60000);

    }

    @Test
    void shutdownTest() throws Exception {
        quartzManager.shutdown();
    }

}
