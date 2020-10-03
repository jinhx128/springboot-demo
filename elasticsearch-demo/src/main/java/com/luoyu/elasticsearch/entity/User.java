package com.luoyu.elasticsearch.entity;

import lombok.Data;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/8 4:57 下午
 * @Version: 1.0.0
 */
@Data
public class User {

    private Long id;
    private String name;
    private String description;
    private int age;

}
