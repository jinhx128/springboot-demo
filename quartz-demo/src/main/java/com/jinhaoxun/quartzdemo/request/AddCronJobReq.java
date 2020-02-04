package com.jinhaoxun.quartzdemo.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 新增Cron定时任务请求实体类
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Setter
@Getter
public class AddCronJobReq {

    private String jobName;

    private String jobGroupName;

    private String triggerName;

    private String triggerGroupName;

    private String jobClass;

    private String date;

    private Map<String, String> params = new HashMap<>();

}
