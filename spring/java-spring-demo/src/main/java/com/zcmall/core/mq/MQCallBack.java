package com.zcmall.core.mq;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ConsumeContext;

public interface MQCallBack {
	public boolean exec(Message message, ConsumeContext context);
}
