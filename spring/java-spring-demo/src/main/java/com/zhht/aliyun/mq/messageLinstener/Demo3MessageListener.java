package com.zhht.aliyun.mq.messageLinstener;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.zhbc.framework.support.TransformForMQ;
import com.zhht.aliyun.mq.util.RecordCheckReq;
import com.zhht.aliyun.mq.util.UserService;

public class Demo3MessageListener implements MessageListener {
	@Autowired
	private UserService mMessageDistribute;
	public Action consume(Message message, ConsumeContext context) {
    	String body = new String(message.getBody());
        System.out.println("消费成功3-Receive: " + message.getKey()+":"+message.getTopic()+":"+body.toString());
        try {
            //do something..
        	Map<String, Class> type = new HashMap<String, Class>();
			type.put("body", RecordCheckReq.class);
			TransformForMQ map = TransformForMQ.fromJson(body, type);
			RecordCheckReq r = (RecordCheckReq)map.getBody(); 
        	String name = mMessageDistribute.getName(JSON.toJSONString(map.getBody()));
        	System.out.println("消费成功3-Receive: 监听接受的数据："+name);
            return Action.CommitMessage;
        } catch (Exception e) {
        	e.printStackTrace();
        	//System.out.println("---"+e.getMessage());
            //消费失败
            return Action.ReconsumeLater;
        }
    }
}
