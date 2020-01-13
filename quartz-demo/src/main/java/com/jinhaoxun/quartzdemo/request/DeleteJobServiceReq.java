package com.jinhaoxun.quartzdemo.request;

import lombok.Getter;
import lombok.Setter;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2020-01-09
 * @description 删除定时任务请求实体类
 */
@Setter
@Getter
public class DeleteJobServiceReq {

    private String jobName;

    private String jobGroupName;

    private String triggerName;

    private String triggerGroupName;

}
