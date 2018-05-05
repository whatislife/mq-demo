package com.zcmall.core.test;

import com.zcmall.core.constain.AliMQTopic;
import com.zcmall.core.mq.MQProducerUtils;

public class ProducerTestM {
	public static void main(String[] args) {
		//MQProducerUtils.sendMQ(topic, tag, body)
		String bodys = "数据你好";
		String reStr = MQProducerUtils.sendMQ(AliMQTopic.DEMO, "TagA", bodys);
		System.out.println("数据返回结果"+reStr);
	}

}
