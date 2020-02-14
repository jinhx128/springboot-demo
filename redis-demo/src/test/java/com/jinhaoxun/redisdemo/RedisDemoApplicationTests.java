package com.jinhaoxun.redisdemo;

import com.jinhaoxun.redisdemo.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class RedisDemoApplicationTests {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Resource
	private RedisUtil redisUtil;

	@Test
	void setStringTest() {
		redisTemplate.opsForValue().set("a","c");
	}

	@Test
	void getStringTest() {
		String value = redisTemplate.opsForValue().get("a").toString();
		log.info(value);
	}

	@Test
	void deteleStringTest() {
		redisTemplate.delete("a");
	}

	@Test
	void redisUtilGetTest() {
		redisUtil.set("a","g");
		String a = redisUtil.get("a").toString();
		log.info(a);
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
