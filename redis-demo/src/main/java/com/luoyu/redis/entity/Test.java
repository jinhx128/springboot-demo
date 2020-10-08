package com.luoyu.redis.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Test implements Serializable {

    private String id;
    private String name;
    private int age;
    private String sex;

}
