package com.zhht.aliyun.mq.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.zhht.aliyun.mq.constain.AliMQTopic;
import com.zhht.aliyun.mq.producer.MQProducer;

@Component
public class ProducerAssignTask {

	@Autowired
	RedisTemplate redisTemplate;
	@Autowired
	private MQProducer producer;
	
	ExecutorService threadPoolOffer = Executors.newSingleThreadExecutor();

	public void runJob() throws Exception {

		threadPoolOffer.execute(new Runnable() {
			public void run() {
				try {
					while (true) {
						try {
							
							String s = (String) redisTemplate.opsForList().leftPop(AliMQTopic.MQ_ASSIGN);
							System.out.println("assignTaskStart-json-"+s);
							if (s == null) {
								Thread.sleep(1000);
								continue;
							}
							// 重新分配数据
						    String body = "数据传递1";
						    producer.sendMQ(AliMQTopic.RECORD_TEST, "TagA", body);
						} catch (Exception e) {
							e.printStackTrace();
							continue;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("出现错误");
				}
			}
		});
	}

}
