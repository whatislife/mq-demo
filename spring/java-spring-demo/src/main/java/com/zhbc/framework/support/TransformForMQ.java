package com.zhbc.framework.support;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;
import net.sf.json.util.JSONUtils;

import com.alibaba.fastjson.JSON;

public class TransformForMQ<T> implements Serializable {

	private static final long serialVersionUID = -8984597410657442040L;
	private String topic;
	private String tag;
	private String key;
	private T body;
	
	public static TransformForMQ<?> fromJson(String json) {
		//JSON.toJSON(javaObject, parserConfig)
		TransformForMQ<?> t=JSON.parseObject(json, TransformForMQ.class);
		return t;
	}
	
	public static TransformForMQ fromJson(String json,Map<String,Class> map){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateFormater()); 
		generate(jsonConfig);
		String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","HH:mm:ss"};  
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(dateFormats,(Date) null)); 
		return (TransformForMQ)JSONObject.toBean(JSONObject.fromObject(json,jsonConfig),TransformForMQ.class,map);
	}

	/*
	 * 对象的属性为Integer类型属性时，如果属性值为null，则json化的字符串属性值为null
	 * */
	private static void generate(JsonConfig jsonConfig){
		DefaultValueProcessor defaultValueProcessor=new DefaultValueProcessor() {
			@Override
			public Object getDefaultValue(Class arg0) {
				return null;
			}
		};
		jsonConfig.registerDefaultValueProcessor(String.class, defaultValueProcessor);
		jsonConfig.registerDefaultValueProcessor(Integer.class, defaultValueProcessor);
		jsonConfig.registerDefaultValueProcessor(Double.class, defaultValueProcessor);
		jsonConfig.registerDefaultValueProcessor(Float.class, defaultValueProcessor);
		jsonConfig.registerDefaultValueProcessor(List.class, defaultValueProcessor);
		jsonConfig.registerDefaultValueProcessor(Date.class, defaultValueProcessor);
	}


	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}




}
