package com.luoyu.shiro.jwt;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @version 1.0
 * @author jinhaoxun
 * @date 2018-05-09
 * @description Josn Web Token
 */
public class Jwt implements AuthenticationToken {

    private String token;

    /**
     * @author jinhaoxun
     * @description 构造器
     * @param token token
     * @return Jwt Jwt
     */
    public Jwt(String token) {
        this.token = token;
    }

    /**
     * @author jinhaoxun
     * @description 获取 token
     * @return Object
     */
    @Override
    public Object getPrincipal() {
        return token;
    }

    /**
     * @author jinhaoxun
     * @description 获取 token
     * @return Object
     */
    @Override
    public Object getCredentials() {
        return token;
    }
}
