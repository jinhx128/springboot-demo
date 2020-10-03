package com.luoyu.mail;

import com.luoyu.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class MailApplicationTests {

	@Autowired
	private MailService mailService;

	@Test
	void sendSimpleMailTest() {
		mailService.sendSimpleMail("xxx", "jhx测试邮件主题", "jhx测试邮件内容");
	}

	@Test
	void sendMimeMailTest1() throws Exception {
		mailService.sendMimeMail("xxx", "jhx测试邮件主题", "jhx测试邮件内容", "/Users/luoyu/Downloads/赤瞳.jpg");
	}

	@Test
	void sendMimeMailTest2() throws Exception {
		mailService.sendMimeMail("xxx", "jhx测试邮件主题");
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
