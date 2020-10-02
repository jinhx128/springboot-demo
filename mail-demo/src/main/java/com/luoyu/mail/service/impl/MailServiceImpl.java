package com.luoyu.mail.service.impl;

import com.luoyu.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String username;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单邮件的接口
     *
     * @param mail     接收邮箱
     * @param subject     主题
     * @param text     内容
     * @return
     */
    @Override
    public boolean sendSimpleMail(String mail, String subject, String text) {
        //创建邮件内容
        SimpleMailMessage message=new SimpleMailMessage();
        //这里指的是发送者的账号
        message.setFrom(username);
        message.setTo(mail);
        message.setSubject(subject);
        message.setText(text);
        //发送邮件
        mailSender.send(message);
        return true;
    }

    @Override
    public boolean sendMimeMail(String mail, String subject, String text, String path) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        // 设置utf-8或GBK编码，否则邮件会有乱码
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        messageHelper.setFrom(username);
        messageHelper.setTo(mail);
        messageHelper.setSubject(subject);
        messageHelper.setText(text, true);
        FileSystemResource file = new FileSystemResource(new File(path));
        String fileName = path.substring(path.lastIndexOf(File.separator));
        messageHelper.addAttachment(fileName,file);
        mailSender.send(mimeMessage);
        return true;
    }

    @Override
    public boolean sendMimeMail(String mail, String subject) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(username);
        helper.setTo(mail);
        helper.setSubject(subject);
        // 注意<img/>标签，src='cid:jpg'，'cid'是contentId的缩写，'jpg'是一个标记
        helper.setText("<html><body><img src=\"cid:jpg\"></body></html>", true);
        // 加载文件资源，作为附件
        FileSystemResource file = new FileSystemResource(new File("/Users/luoyu/Downloads/赤瞳.jpg"));
        // 调用MimeMessageHelper的addInline方法替代成文件('jpg[标记]', file[文件])
        helper.addInline("jpg", file);
        // 发送邮件
        mailSender.send(mimeMessage);
        return true;
    }

}
