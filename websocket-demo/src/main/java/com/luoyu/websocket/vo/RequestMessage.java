package com.luoyu.websocket.vo;

import lombok.Data;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/16 10:39 下午
 * @Version: 1.0.0
 */
@Data
public class RequestMessage {

    private String sender;//消息发送者
    private String room;//房间号
    private String type;//消息类型
    private String content;//消息内容

    public RequestMessage() {
    }

    public RequestMessage(String sender, String room, String type, String content) {
        this.sender = sender;
        this.room = room;
        this.type = type;
        this.content = content;
    }

}
