package com.zhht.aliyun.mq.producer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
import com.zhbc.framework.support.TransformForMQ;
import com.zhht.aliyun.mq.constain.AliMQTopic;
import com.zhht.aliyun.mq.util.User;
import com.zhht.aliyun.mq.util.UserService;
@Service
public class MQProducer {
		
	@Autowired
	private Producer producer;
	@Autowired
	private RedisTemplate redisTemplate;
	@Autowired
	private UserService userService;
	/**
	 * 发送集群MQ 多个消费者只会有一个消费者接收到。
	 * @param topic 可理解为一级类别
	 * @param tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
	 * @param body 消息体
	 * @return 消息的唯一ID 如果返回为null就表示发送失败，主流程不需要管返回
	 */
	public String sendMQ(String topic, String tag, String body) {
		Message msg = new Message(topic, tag, body.getBytes());
		return sendMQ(msg);
	}
	
	/**
	 * 设置延迟发送MQ
	 * @param topic 可理解为一级类别
	 * @param tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
	 * @param body 消息体
	 * @param delayTime 延迟的时间单位毫秒
	 * @return 消息的唯一ID 如果返回为null就表示发送失败，主流程不需要管返回
	 */
	public String sendMQDelayTime(String topic, String tag, String body,long delayTime){
		Message msg = new Message(topic, tag, body.getBytes());
		msg.setStartDeliverTime(System.currentTimeMillis() + delayTime);
		return sendMQ(msg);
	}

	/**
	 * 指定时间发送MQ
	 * @param topic 可理解为一级类别
	 * @param tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
	 * @param body 消息体
	 * @param date 指定时间发送消息
	 * @return 消息的唯一ID 如果返回为null就表示发送失败，主流程不需要管返回
	 */
	public String sendMqDelayDate(String topic, String tag, String body,Date date){
		Message msg = new Message(topic, tag, body.getBytes());
		msg.setStartDeliverTime(date.getTime());
		return sendMQ(msg);
	}
	
	

	/**
	 * 发送消息
	 * @param msg
	 * @return
	 */
	private String sendMQ(Message msg) {
		System.out.println(userService.getName("songjian"));
		String body = new String(msg.getBody());
		final TransformForMQ map = JSON.parseObject(body, new TypeReference<TransformForMQ<Map<String, Object>>>(){});
		//Producer producer = ONSFactory.createProducer(props);
		// 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        producer.start();
        String messageId = "";
		try {
//			User user = null;
//			user.getName();
			producer.sendAsync(msg, new SendCallback() {
                @Override
                public void onSuccess(final SendResult sendResult) {
                    System.out.println(sendResult);
                 // 消费发送成功
                    System.out.println("send message success. topic=" + sendResult.getTopic() + ", msgId=" + sendResult.getMessageId());
                }

                @Override
                public void onException(final OnExceptionContext context) {
                	// 消息发送失败
                    System.out.println("send message failed. topic=" + context.getTopic() + ", msgId=" + context.getMessageId());
                    //出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
                	redisTemplate.opsForList().rightPush(AliMQTopic.MQ_ASSIGN, map);
                }
            });
			// 在callback返回之前即可取得msgId。
		    System.out.println("send message async. topic=" + msg.getTopic() + ", msgId=" + msg.getMsgID());
		    messageId = msg.getMsgID();
//        } catch (ONSClientException e) {
		 } catch (Exception e) {
			// 出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
			// 吃掉这个异常不影响主流程
        	messageId = null;
			System.out.println("###########消息队列发送失败：" + e.getMessage());
			System.out.println("数据异常2"+JSON.toJSONString(map));
        	redisTemplate.opsForList().rightPush(AliMQTopic.MQ_ASSIGN, map);
        }
		
		producer.shutdown();
		return messageId;
	}

}
