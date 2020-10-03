package com.luoyu.quartz.request;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 新增Simple定时任务请求实体类
 * @Author: jinhaoxun
 * @Date: 2020/1/15 11:20
 * @Version: 1.0.0
 */
@Data
public class AddSimpleJobReq {

    private String jobClass;

    private Date date;

    private Map<String, String> params = new HashMap<>();

}
