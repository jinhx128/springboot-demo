package com.luoyu.redis;

import com.luoyu.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class RedisApplicationTests {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private RedisUtil redisUtil;

	@Test
	void redisTemplateSetStringTest() {
		redisTemplate.opsForValue().set("a","c");
	}

	@Test
	void redisTemplateGetStringTest() {
		log.info(redisTemplate.opsForValue().get("a").toString());
	}

	@Test
	void redisUtilDeteleStringTest() {
		redisTemplate.delete("a");
	}

	@Test
	void redisUtilGetStringTest() {
		log.info(redisUtil.get("a").toString());
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
