package com.zhht.aliyun.mq.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
@Component
public class JobListener implements	ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	ProducerAssignTask producerAssignTask;
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (event.getApplicationContext().getParent() == null) {
			try {
				System.out.println("assignTaskStart");
				producerAssignTask.runJob();
				System.out.println("assignTaskEnd");
			} catch (Exception e) {
				System.out.println("监听出错");
			}
		}
	}
	
	
	
}

