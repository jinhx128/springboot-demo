package com.luoyu.kafka.message;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Description: 消息体
 * @Author: jinhaoxun
 * @Date: 2020/7/10 9:53 上午
 * @Version: 1.0.0
 */
@Getter
@Setter
public class Message<T> implements Serializable {
    private String id;
    private T content;
}
