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

public class TransformForWS<T> implements Serializable {

	private static final long serialVersionUID = -8984597410657442040L;
	
	private String k;
	private String code;
	private String oprNum;
	private String ip;
	private String signature;
	private T data;
	/*@Deprecated
	public static TransformForWS fromJson(String json, Class clazz) {
		JsonConfig jsonConfig = new JsonConfig();  
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateFormater()); 
		generate(jsonConfig);
		Map<String,Class> map = new HashMap<String,Class>();
		map.put("data", clazz);
		String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","HH:mm:ss"};  
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(dateFormats,(Date) null)); 
		return (TransformForWS)JSONObject.toBean(JSONObject.fromObject(json,jsonConfig),TransformForWS.class,map);
	}*/
	public static TransformForWS<?> fromJson(String json) {
		//JSON.toJSON(javaObject, parserConfig)
		TransformForWS<?> t=JSON.parseObject(json, TransformForWS.class);
		return t;
	}
	
	public static TransformForWS fromJson(String json,Map<String,Class> map){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class, new JsonDateFormater()); 
		generate(jsonConfig);
		String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd","HH:mm:ss"};  
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpherEx(dateFormats,(Date) null)); 
		return (TransformForWS)JSONObject.toBean(JSONObject.fromObject(json,jsonConfig),TransformForWS.class,map);
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
	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOprNum() {
		return oprNum;
	}

	public void setOprNum(String oprNum) {
		this.oprNum = oprNum;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

}
