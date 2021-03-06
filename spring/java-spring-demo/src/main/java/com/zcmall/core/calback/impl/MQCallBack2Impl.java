package com.zcmall.core.calback.impl;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.zcmall.core.calback.MQCallBack;

public class MQCallBack2Impl implements MQCallBack {

	@Override
	public boolean exec(Message message, ConsumeContext context) {
		String body = new String(message.getBody());
		System.out.println("消费成功2-Receive: " + body);
        try {
            //do something..
            return false;
        } catch (Exception e) {
            //消费失败
            return false;
        }
	}

}
