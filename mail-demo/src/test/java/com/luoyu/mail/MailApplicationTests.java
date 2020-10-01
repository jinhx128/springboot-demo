package com.luoyu.mail;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
// 获取启动类，加载配置，确定装载 Spring 程序的装载方法，它回去寻找 主配置启动类（被 @SpringBootApplication 注解的）
@SpringBootTest
class MailApplicationTests {

	@Value("${spring.mail.username}")
	private String username;

	@Autowired
	private JavaMailSender mailSender;

	@Test
	void test() {
		//创建邮件内容
		SimpleMailMessage message=new SimpleMailMessage();
		//这里指的是发送者的账号
		message.setFrom(username);
		message.setTo("956534763@qq.com");
		message.setSubject("jhx测试邮件主题");
		message.setText("jhx测试邮件内容");
		//发送邮件
		mailSender.send(message);
	}

	@Test
	void test1() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		// 设置utf-8或GBK编码，否则邮件会有乱码
		MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		messageHelper.setFrom(username);
		messageHelper.setTo(username);
		messageHelper.setSubject("jhx测试邮件主题");
		messageHelper.setText("jhx测试邮件主题", true);
		FileSystemResource file=new FileSystemResource(new File("/Users/luoyu/Downloads/赤瞳.jpg"));
		String fileName="/Users/luoyu/Downloads/赤瞳.jpg".substring("/Users/luoyu/Downloads/赤瞳.jpg".lastIndexOf(File.separator));
		messageHelper.addAttachment(fileName,file);
		mailSender.send(mimeMessage);
	}

	@Test
	void test2() throws Exception {
		MimeMessage mimeMessage = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom(username);
		helper.setTo("956534763@qq.com");
		helper.setSubject("jhx测试邮件主题");
		// 注意<img/>标签，src='cid:jpg'，'cid'是contentId的缩写，'jpg'是一个标记
		helper.setText("<html><body><img src=\"cid:jpg\"></body></html>", true);
		// 加载文件资源，作为附件
		FileSystemResource file = new FileSystemResource(new File("/Users/luoyu/Downloads/赤瞳.jpg"));
		// 调用MimeMessageHelper的addInline方法替代成文件('jpg[标记]', file[文件])
		helper.addInline("jpg", file);
		// 发送邮件
		mailSender.send(mimeMessage);
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
