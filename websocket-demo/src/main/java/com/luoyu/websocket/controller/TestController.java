package com.luoyu.websocket.controller;

import com.luoyu.websocket.vo.RequestMessage;
import com.luoyu.websocket.vo.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/16 10:39 下午
 * @Version: 1.0.0
 */
@RestController
public class TestController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 单聊
     * @CrossOrigin 跨域
     *
     * @MessageMapping 注解的方法可以使用下列参数:
     * * 使用@Payload方法参数用于获取消息中的payload（即消息的内容）
     * * 使用@Header 方法参数用于获取特定的头部
     * * 使用@Headers方法参数用于获取所有的头部存放到一个map中
     * * java.security.Principal 方法参数用于获取在websocket握手阶段使用的用户信息
     * @param requestMessage
     * @throws Exception
     */
    @CrossOrigin
    @MessageMapping("/chat")
    public void messageHandling(RequestMessage requestMessage) throws Exception {
        String destination = "/topic/" + HtmlUtils.htmlEscape(requestMessage.getRoom());

        String sender = HtmlUtils.htmlEscape(requestMessage.getSender());  //htmlEscape  转换为HTML转义字符表示
        String type = HtmlUtils.htmlEscape(requestMessage.getType());
        String content = HtmlUtils.htmlEscape(requestMessage.getContent());
        ResponseMessage response = new ResponseMessage(sender, type, content);

        messagingTemplate.convertAndSend(destination, response);
    }

    /**
     * 群发消息
     * @param requestMessage
     * @return
     * @throws Exception
     */
    @CrossOrigin
    @MessageMapping("/chatAll")
    public void messageHandlingAll(RequestMessage requestMessage) throws Exception {
        String destination = "/all";
        String sender = HtmlUtils.htmlEscape(requestMessage.getSender());  //htmlEscape  转换为HTML转义字符表示
        String type = HtmlUtils.htmlEscape(requestMessage.getType());
        String content = HtmlUtils.htmlEscape(requestMessage.getContent());
        ResponseMessage response = new ResponseMessage(sender, type, content);

        messagingTemplate.convertAndSend(destination, response);
    }

}
