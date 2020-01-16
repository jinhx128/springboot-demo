package com.jinhaoxun.quartzdemo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description: 删除定时任务请求实体类
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Setter
@Getter
public class DeleteJobServiceReq {

    private String jobName;

    private String jobGroupName;

    private String triggerName;

    private String triggerGroupName;

}
