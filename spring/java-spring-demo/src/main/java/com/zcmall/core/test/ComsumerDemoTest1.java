package com.zcmall.core.test;

import com.zcmall.core.calback.MQCallBack;
import com.zcmall.core.calback.impl.MQCallBack1Impl;
import com.zcmall.core.constain.AliMQTopic;
import com.zcmall.core.mq.MQConsumerListener;

public class ComsumerDemoTest1 {
	public static void main(String[] args) {
		MQCallBack mqCallBack = new MQCallBack1Impl();
		//集群
		MQConsumerListener.MQListener(AliMQTopic.RECORD_TEST, "TagA", mqCallBack, "CID_RECORD_TEST_01");
		//广播
//		MQConsumerListener.MQPubSubListener(AliMQTopic.RECORD_TEST, "TagA", mqCallBack, "CID_RECORD_TEST_01");
	}

}
