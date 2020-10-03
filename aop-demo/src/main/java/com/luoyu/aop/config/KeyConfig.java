package com.luoyu.aop.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class KeyConfig {

    /**
     * 解密请求体默认key
     */
    @Value("${http.check.key.aes.request}")
    private String keyAesRequest;

    /**
     * 加密响应体默认key
     */
    @Value("${http.check.key.aes.response}")
    private String KeyAesResponse;

    /**
     * 请求时间超时时间，单位秒
     */
    @Value("${http.check.request.timeout}")
    private String timeout;

}
