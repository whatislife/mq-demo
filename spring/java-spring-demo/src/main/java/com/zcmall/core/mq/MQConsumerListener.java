package com.zcmall.core.mq;

import java.util.Properties;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;

public class MQConsumerListener {
	//private static Log log = LogFactory.getLog(MQProducerUtils.class);

	private static Properties props;
	
	private static String LOG_INFO = "启动集群订阅：topic=%s,tag=%s,consumerId=%s,callBack=%s";
	
	static {
		props = new Properties();
//		props.put(PropertyKeyConst.AccessKey, MQFileConfig.getValue(MQConfigEnum.ACCESS_KEY));
//		props.put(PropertyKeyConst.SecretKey, MQFileConfig.getValue(MQConfigEnum.SECRET_KEY));
		// AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
		props.put(PropertyKeyConst.AccessKey, "LTAIya0znEl2jTbR");
		// SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
		props.put(PropertyKeyConst.SecretKey, "eCQxgSdixWNaPZFh2d40z890lNUnUa");
	}

	/**
	 * 集群订阅消息
	 * @param topic MQ主题
	 * @param tag  二级类别
	 * @param callBack 回调包名+类名
	 * @param consumerId 订阅者ID
	 */
	public static void MQListener(String topic, String tag, final MQCallBack callBack,String consumerId){
		props.put(PropertyKeyConst.ConsumerId, consumerId);
//		props.put(PropertyKeyConst.MessageModel,PropertyValueConst.CLUSTERING);
		Consumer consumer = ONSFactory.createConsumer(props);
		System.out.println(consumer);
		consumer.subscribe(topic, tag, new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
            	boolean bool = callBack.exec(message, context);
            	Action result = Action.CommitMessage;
            	if(!bool){
            		result = Action.ReconsumeLater;
            	}
                return result;
            }
        });
		consumer.start();
		//log.info(String.format(LOG_INFO, topic,tag,consumerId,callBack));
		System.out.println(topic+"|"+tag+"|"+consumerId);
	}
	
	/**
	 * 广播的方式发送MQ
	 * @param topic MQ主题
	 * @param tag 二级类别
	 * @param callBack 回调包名+类名
	 * @param consumerId 订阅者ID
	 */
	public static void MQPubSubListener(final String topic,final String tag, final MQCallBack callBack,String consumerId){
		props.put(PropertyKeyConst.MessageModel,PropertyValueConst.BROADCASTING);
        Consumer consumer = ONSFactory.createConsumer(props);
        consumer.subscribe(topic, tag, new MessageListener() {
            public Action consume(Message message, ConsumeContext context) {
            	boolean bool = callBack.exec(message, context);
            	Action result = Action.CommitMessage;
            	if(!bool){
            		result = Action.ReconsumeLater;
            	}
                return result;
            }
        });
        consumer.start();
        //log.info(String.format(LOG_INFO, topic,tag,consumerId,callBack));
        System.out.println(topic+"|"+tag+"|"+consumerId);
	}
}
