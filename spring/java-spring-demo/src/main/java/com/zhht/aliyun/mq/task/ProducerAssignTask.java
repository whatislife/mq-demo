package com.zhht.aliyun.mq.task;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zhbc.framework.support.TransformForMQ;
import com.zhbc.framework.support.TransformForWS;
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
//							String s = (String) redisTemplate.opsForList().leftPop(AliMQTopic.MQ_ASSIGN);
//							System.out.println("assignTaskStart-json-"+s);
//							if (s == null) {
//								Thread.sleep(1000);
//								continue;
//							}
							

							TransformForMQ p = (TransformForMQ) redisTemplate.opsForList().leftPop(AliMQTopic.MQ_ASSIGN);
							System.out.println("assignTaskStart-json-"+JSON.toJSONString(p));
							if (p == null) {
								Thread.sleep(1000);
								continue;
							}
							// 重新分配数据
//					    	TransformForMQ p = new TransformForMQ();
//					    	p =	JSON.parseObject(s, new TypeReference<TransformForMQ<Map<String, Object>>>(){});
					    	//TransformForMQ p =	JSON.parseObject(s, TransformForMQ.class);
					    	System.out.println("==================="+p.getTopic()+":"+p.getTag()+":"+p.getBody());
//						    producer.sendMQ(AliMQTopic.RECORD_TEST, "TagA", JSON.toJSONString(p.getBody()));
						    producer.sendMQ(p.getTopic(), p.getTag(), p.getBody());
							
							/*// 重新分配数据
//					    	TransformForMQ p = new TransformForMQ();
//					    	p =	JSON.parseObject(s, new TypeReference<TransformForMQ<Map<String, Object>>>(){});
					    	TransformForMQ p =	JSON.parseObject(s, TransformForMQ.class);
					    	System.out.println("==================="+p.getTopic()+":"+p.getTag()+":"+p.getBody());
//						    producer.sendMQ(AliMQTopic.RECORD_TEST, "TagA", JSON.toJSONString(p.getBody()));
						    producer.sendMQ(p.getTopic(), p.getTag(), p.getBody());*/
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
