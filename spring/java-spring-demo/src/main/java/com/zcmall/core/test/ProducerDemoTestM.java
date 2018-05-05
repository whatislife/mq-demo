package com.zcmall.core.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ProducerDemoTestM {

	public static void main(String[] args) {
		  ApplicationContext context = new ClassPathXmlApplicationContext("consumer_demo.xml");
	      System.out.println("Consumer Started");
	}
}
