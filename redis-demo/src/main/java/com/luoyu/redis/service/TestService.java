package com.luoyu.redis.service;

import com.luoyu.redis.entity.Test;

public interface TestService {

    Test get(String id);

    boolean add(Test test);

    boolean delete(String id);

    boolean update(Test test);

}
