package com.zhht.aliyun.mq.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhht.aliyun.mq.constain.AliMQTopic;
import com.zhht.aliyun.mq.producer.MQProducer;

public class ProducerDemo2Test {
	public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("producer_xml_demo.xml");
	     MQProducer producer = (MQProducer)context.getBean("MQProducer");
	     String body = "数据传递";
	     producer.sendMQ(AliMQTopic.RECORD_TEST, "TagA", body);
	}

}
