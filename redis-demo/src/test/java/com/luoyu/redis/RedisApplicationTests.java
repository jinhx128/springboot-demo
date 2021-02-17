package com.luoyu.redis;

import com.luoyu.redis.service.TestService;
import com.luoyu.redis.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class RedisApplicationTests {

	@Resource
	private RedisTemplate<String, Object> redisTemplate;

	@Autowired
	private TestService testService;

	@Autowired
	private RedisUtils redisUtils;

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
		log.info(redisUtils.get("a").toString());
	}

	@Test
	void redisTemplatePipelinedGetTest() {
		testService.gets().forEach(x -> log.info(x.toString()));
	}

	@Test
	void redisTemplatePipelinedSetTest() {
		List<com.luoyu.redis.entity.Test> testList = new ArrayList<>();
		for (int i = 0; i < 500; i++) {
			com.luoyu.redis.entity.Test test = new com.luoyu.redis.entity.Test();
			test.setId(i + "");
			test.setAge(i * i);
			test.setName("name" + i);
			test.setSex("sex" + i);
			testList.add(test);
		}
		testService.sets(testList);
	}

	@Test
	void redisTemplatePipelinedDeleteTest() {
		testService.deletes();
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
