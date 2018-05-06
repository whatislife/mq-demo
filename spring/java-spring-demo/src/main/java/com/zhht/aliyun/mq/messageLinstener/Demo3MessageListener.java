package com.zhht.aliyun.mq.messageLinstener;


import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.zhht.aliyun.mq.util.RecordCheckReq;
import com.zhht.aliyun.mq.util.UserService;

public class Demo3MessageListener implements MessageListener {
	@Autowired
	private UserService mMessageDistribute;
	public Action consume(Message message, ConsumeContext context) {
    	String body = new String(message.getBody());
        System.out.println("消费成功3-Receive: " + message.getKey()+":"+body.toString());
        try {
            //do something..
        	//UserService mMessageDistribute = (UserService)SpringContextUtils.getBean("userService"); 
        	RecordCheckReq req = JSON.parseObject(body.toString(), RecordCheckReq.class);
        	String name = mMessageDistribute.getName(JSON.toJSONString(req));
        	System.out.println("监听接受的数据："+name);
            return Action.CommitMessage;
        } catch (Exception e) {
        	e.printStackTrace();
        	//System.out.println("---"+e.getMessage());
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
