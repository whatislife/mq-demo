package com.zhht.aliyun.mq.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zhht.aliyun.mq.constain.AliMQTopic;
import com.zhht.aliyun.mq.producer.MQProducer;

public class ProducerDemoTest {
	public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("producer_xml_demo.xml");
	     MQProducer producer = (MQProducer)context.getBean("MQProducer");
	     for (int i = 0; i < 2; i++) {
	    	 String body = "数据传递"+i;
		    String messageId =  producer.sendMQ(AliMQTopic.RECORD_TEST, "TagA", body);
		    System.out.println("返回的数据是："+messageId);
	     }
	     
	     //失败重试数据格式 topic tag body key 
	}

}
