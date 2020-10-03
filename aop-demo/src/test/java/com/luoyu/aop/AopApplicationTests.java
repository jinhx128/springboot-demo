package com.luoyu.aop;

import com.luoyu.aop.config.KeyConfig;
import com.luoyu.aop.entity.response.TestResponse;
import com.luoyu.aop.util.AESUtil;
import com.luoyu.aop.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class AopApplicationTests {

    @Autowired
    private KeyConfig keyConfig;

    @Test
    void AESEncryptRequestTest() throws Exception {
        TestResponse testResponse = new TestResponse();
        testResponse.setId(1);
        testResponse.setName("test");
        testResponse.setSex("男");

        String encrypt = AESUtil.encrypt(JsonUtils.objectToJson(testResponse), keyConfig.getKeyAesRequest());
        log.info(encrypt);
    }

    @Test
    void AESDecryptRequestTest() throws Exception {
        String str = "AdChWsCSrehSLAJVUalBseXKZ7BVQ0RS5hd5EryE+hE2GZ+upLPM1hR2kgCwseeF";

        String decrypt = AESUtil.decrypt(str, keyConfig.getKeyAesRequest());
        log.info(decrypt);
    }

    @Test
    void AESEncryptResponseTest() throws Exception {
        TestResponse testResponse = new TestResponse();
        testResponse.setId(1);
        testResponse.setName("test");
        testResponse.setSex("男");

        String encrypt = AESUtil.encrypt(JsonUtils.objectToJson(testResponse), keyConfig.getKeyAesResponse());
        log.info(encrypt);
    }

    @Test
    void AESDecryptResponseTest() throws Exception {
        String str = "ReXg0r2PHewqdtD/ucSUU05UtLcbNSaPiTWzQj6EHGqtDrokVclzeTMlow5OPthC";

        String decrypt = AESUtil.decrypt(str, keyConfig.getKeyAesResponse());
        log.info(decrypt);
    }


    @BeforeEach
    void testBefore(){
        log.info("测试开始!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @AfterEach
    void testAfter(){
        log.info("测试结束!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
