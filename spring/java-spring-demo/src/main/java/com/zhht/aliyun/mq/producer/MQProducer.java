package com.zhht.aliyun.mq.producer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.OnExceptionContext;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.exception.ONSClientException;
@Service
public class MQProducer {
		
	@Autowired
	private Producer producer;
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
		//Producer producer = ONSFactory.createProducer(props);
		// 在发送消息前，必须调用 start 方法来启动 Producer，只需调用一次即可
        producer.start();
		try {
            producer.sendAsync(msg, new SendCallback() {
                @Override
                public void onSuccess(final SendResult sendResult) {
                	System.out.println("数据发送成功");
                    System.out.println(sendResult);
                }

                @Override
                public void onException(final OnExceptionContext context) {
                    //出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
                }
            });
        } catch (ONSClientException e) {
			// 出现异常意味着发送失败，为了避免消息丢失，建议缓存该消息然后进行重试。
			// 吃掉这个异常不影响主流程
			// log.error("###########消息队列发送失败：" + e.getMessage());
			System.out.println("###########消息队列发送失败：" + e.getMessage());
        }
		producer.shutdown();
		return null;
	}

}