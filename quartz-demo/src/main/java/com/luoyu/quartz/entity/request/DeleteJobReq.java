package com.luoyu.quartz.entity.request;

import lombok.Data;

/**
 * @Description: 删除定时任务请求实体类
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Data
public class DeleteJobReq {

    private String jobName;

    private String jobGroupName;

    private String triggerName;

    private String triggerGroupName;

}
