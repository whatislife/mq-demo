package com.zhht.aliyun.mq.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.zhbc.framework.support.TransformForMQ;
import com.zhht.aliyun.mq.constain.AliMQTopic;
import com.zhht.aliyun.mq.producer.MQProducer;
import com.zhht.aliyun.mq.util.RecordCheckReq;

public class ProducerDemoName2Test {
	public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("producer_xml_demo.xml");
	     MQProducer producer = (MQProducer)context.getBean("MQProducer");
	     for (int i = 0; i < 1; i++) {
	    	 //请求数据
	    	 RecordCheckReq req = new RecordCheckReq();
	    	 req.setCrossName("北出口2");
	    	 req.setParkId("parkId00002");
	    	 System.out.println(JSON.toJSONString(req));
	    	 
	    	 TransformForMQ mq = new TransformForMQ();
	    	 mq.setTopic(AliMQTopic.RECORD_TEST);
	    	 mq.setTag("name2");
	    	 mq.setBody(req);
	    	 
		    String messageId =  producer.sendMQ(AliMQTopic.RECORD_TEST, "name2", JSON.toJSONString(mq));
		    System.out.println("返回的数据是："+messageId);
	     }
	}

}
