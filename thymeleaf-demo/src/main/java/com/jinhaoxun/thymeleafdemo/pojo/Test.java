package com.jinhaoxun.thymeleafdemo.pojo;

import java.io.Serializable;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/4/12 下午1:24
 * @Version: 1.0.0
 */
public class Test implements Serializable {

    private String id;
    private String name;
    private int age;
    private String sex;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
