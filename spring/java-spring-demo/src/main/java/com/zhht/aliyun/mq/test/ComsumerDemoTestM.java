package com.zhht.aliyun.mq.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ComsumerDemoTestM {

	public static void main(String[] args) {
		//ApplicationContext context1 = new ClassPathXmlApplicationContext("producer_xml_demo.xml");
		ApplicationContext context = new ClassPathXmlApplicationContext("consumer_xml_demo.xml");
	    System.out.println("Consumer Started");
	}
}
