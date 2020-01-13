package com.jinhaoxun.quartzdemo.request;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2020-01-09
 * @description 新增Cron定时任务请求实体类
 */
@Setter
@Getter
public class AddCronJobServiceReq {

    private String jobName;

    private String jobGroupName;

    private String triggerName;

    private String triggerGroupName;

    private String jobClass;

    private String date;

    private Map<String, String> params = new HashMap<>();

}
