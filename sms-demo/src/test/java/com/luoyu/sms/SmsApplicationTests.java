package com.luoyu.sms;

import com.luoyu.sms.service.AliYunSmsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class SmsApplicationTests {

	@Autowired
	private AliYunSmsService aliYunSmsService;

//	@Autowired
//	private SmsService smsService;

//	@Test
//	void test() {
//		// 具体配置请参照具体运营商
//		// your template params
//		String[] paramst = {"5678"};
//		TencentSmsRequest tencentSmsRequest = new TencentSmsRequest();
//		tencentSmsRequest.setPhoneNumber(new String[]{"xxx"});
//		tencentSmsRequest.setParams(paramst);
//		smsService.sendTemplateSms("SMS_180347872", tencentSmsRequest);
//	}

 	@Test
	void aliYunSendSmsTest() {
 		String phone = "xxx";
 		String code = "10086";
 		aliYunSmsService.sendSms(phone, code);
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
