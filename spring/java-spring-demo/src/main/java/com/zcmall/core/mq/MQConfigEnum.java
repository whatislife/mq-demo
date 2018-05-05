package com.zcmall.core.mq;

/**
 * 阿里MQ
 *
 * @author gjl gjl@163.com
 *
 */
public enum MQConfigEnum {
	/**
	 * 发送端ID
	 */
	PRODUCER_ID("producer.id"),
	/**
	 * 消费端ID
	 */
	CONSUMER_ID("consumer.id"),
	/**
	 * 阿里MQ配置
	 */
	ACCESS_KEY("access.key"),
	/**
	 * MQ订阅者
	 */
	LISTENER("listener"),

	/**
	 * 阿里MQ配置
	 */
	SECRET_KEY("secret.key");


	private String key;

	private MQConfigEnum(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
