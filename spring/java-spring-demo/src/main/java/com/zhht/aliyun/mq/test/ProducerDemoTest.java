package com.zhht.aliyun.mq.test;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhbc.framework.support.TransformForWS;
import com.zhht.aliyun.mq.constain.AliMQTopic;
import com.zhht.aliyun.mq.producer.MQProducer;
import com.zhht.aliyun.mq.util.RecordCheckReq;

public class ProducerDemoTest {
	public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("producer_xml_demo.xml");
	     MQProducer producer = (MQProducer)context.getBean("MQProducer");
	     for (int i = 0; i < 1; i++) {
	    	 //请求数据
	    	 RecordCheckReq req = new RecordCheckReq();
	    	 req.setCrossName("北出口");
	    	 req.setParkId("parkId00001");
	    	 System.out.println(JSON.toJSONString(req));
		    String messageId =  producer.sendMQ(AliMQTopic.RECORD_TEST, "name", JSON.toJSONString(req));
		    System.out.println("返回的数据是："+messageId);
	     }
	     
	     //失败重试数据格式 topic tag body key 
	}

}
