package com.zhht.aliyun.mq.messageLinstener;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;

public class Demo3MessageListener implements MessageListener {

    public Action consume(Message message, ConsumeContext context) {
    	String body = new String(message.getBody());
        System.out.println("消费成功3-Receive: " + message.getKey()+":"+body.toString());
        try {
            //do something..
            return Action.CommitMessage;
        } catch (Exception e) {
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
