package com.luoyu.thymeleaf.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/4/12 下午1:24
 * @Version: 1.0.0
 */
@Data
public class Test implements Serializable {

    private String id;
    private String name;
    private int age;
    private String sex;

}
