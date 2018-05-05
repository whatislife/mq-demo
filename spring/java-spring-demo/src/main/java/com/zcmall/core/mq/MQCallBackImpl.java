package com.zcmall.core.mq;

import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;

public class MQCallBackImpl implements MQCallBack {

	@Override
	public boolean exec(Message message, ConsumeContext context) {
		System.out.println("消费成功-Receive: " + message);
        try {
            //do something..
            return true;
        } catch (Exception e) {
            //消费失败
            return false;
        }
	}

}
