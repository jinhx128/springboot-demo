package com.luoyu.mail.service;

public interface MailService {

    /**
     * 发送简单邮件的接口
     *
     * @param mail     接收邮箱
     * @param subject     主题
     * @param text     内容
     * @return
     */
    boolean sendSimpleMail(String mail, String subject, String text);

    /**
     * 发送带附件邮件的接口
     *
     * @param mail     接收邮箱
     * @param subject     主题
     * @param text     内容
     * @param path     附近路径
     * @return
     */
    boolean sendMimeMail(String mail, String subject, String text, String path) throws Exception ;

    /**
     * 发送带附件邮件的接口，并且正文显示附件内容
     *
     * @param mail     接收邮箱
     * @param subject     主题
     * @return
     */
    boolean sendMimeMail(String mail, String subject) throws Exception ;

}
