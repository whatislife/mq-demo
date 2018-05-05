package com.zcmall.core.messageListener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

public class Demo2MessageListener implements MessageListener {

    public Action consume(Message message, ConsumeContext context) {
    	String body = new String(message.getBody());
        System.out.println("消费成功2-Receive: " + body.toString());
        try {
            //do something..
            return Action.CommitMessage;
        } catch (Exception e) {
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
