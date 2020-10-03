package com.luoyu.rocketmq.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 消息体
 * @Author: jinhaoxun
 * @Date: 2020/2/11 上午9:52
 * @Version: 1.0.0
 */
@Data
public class Message<T> implements Serializable {
    private String id;
    private T content;
}
