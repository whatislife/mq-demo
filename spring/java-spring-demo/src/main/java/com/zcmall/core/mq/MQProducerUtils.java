package com.zcmall.core.mq;

import java.util.Date;
import java.util.Properties;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.SendResult;
//import com.zcmall.core.log.Log;
//import com.zcmall.core.log.LogFactory;

public class MQProducerUtils {

//	private static Log log = LogFactory.getLog(MQProducerUtils.class);

	private static Properties props;

	static {
		props = new Properties();
//		props.put(PropertyKeyConst.ProducerId, MQFileConfig.getValue(MQConfigEnum.PRODUCER_ID));
//		props.put(PropertyKeyConst.AccessKey, MQFileConfig.getValue(MQConfigEnum.ACCESS_KEY));
//		props.put(PropertyKeyConst.SecretKey, MQFileConfig.getValue(MQConfigEnum.SECRET_KEY));
		props.put(PropertyKeyConst.ProducerId, "PID_RECORD_TEST_01");
		// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
		props.put(PropertyKeyConst.AccessKey, "LTAIya0znEl2jTbR");
		// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
		props.put(PropertyKeyConst.SecretKey, "eCQxgSdixWNaPZFh2d40z890lNUnUa");
		
	}

	/**
	 * 发送集群MQ 多个消费者只会有一个消费者接收到。
	 * @param topic 可理解为一级类别
	 * @param tag 可理解为Gmail中的标签，对消息进行再归类，方便Consumer指定过滤条件在ONS服务器过滤
	 * @param body 消息体
	 * @return 消息的唯一ID 如果返回为null就表示发送失败，主流程不需要管返回
	 */
	public static String sendMQ(String topic, String tag, String body) {
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
	public static String sendMQDelayTime(String topic, String tag, String body,long delayTime){
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
	public static String sendMqDelayDate(String topic, String tag, String body,Date date){
		Message msg = new Message(topic, tag, body.getBytes());
		msg.setStartDeliverTime(date.getTime());
		return sendMQ(msg);
	}

	/**
	 * 发送消息
	 * @param msg
	 * @return
	 */
	private static String sendMQ(Message msg) {
		try {
			Producer producer = ONSFactory.createProducer(props);
			// 在发送消息前，必须调用start方法来启动Producer，只需调用一次即可。
			producer.start();
			// 发送消息，只要不抛异常就是成功
			SendResult sendResult = producer.send(msg);
			producer.shutdown();
			return sendResult.getMessageId();
		} catch (Exception e) {
			// 吃掉这个异常不影响主流程
			//log.error("###########消息队列发送失败：" + e.getMessage());
			System.out.println("###########消息队列发送失败：" + e.getMessage());
		}
		return null;
	}
}
