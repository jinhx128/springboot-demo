package com.luoyu.rocketmq.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description: 消息体
 * @Author: jinhaoxun
 * @Date: 2020/2/11 上午9:52
 * @Version: 1.0.0
 */
@Getter
@Setter
public class Message<T> implements Serializable {
    private String id;
    private T content;
}
