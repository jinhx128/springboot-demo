package com.luoyu.websocket.vo;

import lombok.Getter;
import lombok.Setter;

/**
 * @Description:
 * @Author: jinhaoxun
 * @Date: 2020/7/16 10:39 下午
 * @Version: 1.0.0
 */
@Setter
@Getter
public class ResponseMessage {

    private String sender;
    private String type;
    private String content;

    public ResponseMessage() {
    }
    public ResponseMessage(String sender, String type, String content) {
        this.sender = sender;
        this.type = type;
        this.content = content;
    }

}
