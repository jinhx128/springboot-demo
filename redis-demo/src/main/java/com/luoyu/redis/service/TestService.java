package com.luoyu.redis.service;

import com.luoyu.redis.entity.Test;

import java.util.List;

public interface TestService {

    Test get(String id);

    boolean add(Test test);

    boolean delete(String id);

    boolean update(Test test);

    List<Test> gets();

    void sets(List<Test> tests);

    void deletes();

}
