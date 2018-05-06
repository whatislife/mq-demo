package com.zhht.aliyun.mq.util;

import com.zhbc.framework.vo.SuperVO;

public class RecordCheckReq extends SuperVO {
	private static final long serialVersionUID = -8162024996189667235L;
	//	停车场ID
	private String parkId;
	//需要被校验的停车记录编码
	private String sourceRecordCode;
	//校验后停车记录编码
	private String targetRecordCode;
	//校验类型 1 - 车牌校验，3-入场记录匹配
	private Integer dataType;
	//出入场类型 0-入场 1-出场
	private Integer type;
	//出口/入口单元名称
	private String crossName;
	//车牌号码 1、无车牌 2、非机动车 3、具体车牌例如京A88888
	private String plateNumber;
	//校验结果 0 -失败 1-成功
	private Integer checkResult;
	//序列号，请求唯一
	private String operateNum;
	//MS是否开闸  0 不开闸 1 开闸
	private Integer open;

	public String getParkId() {
		return parkId;
	}

	public void setParkId(String parkId) {
		this.parkId = parkId;
	}

	public String getSourceRecordCode() {
		return sourceRecordCode;
	}

	public void setSourceRecordCode(String sourceRecordCode) {
		this.sourceRecordCode = sourceRecordCode;
	}

	public String getTargetRecordCode() {
		return targetRecordCode;
	}

	public void setTargetRecordCode(String targetRecordCode) {
		this.targetRecordCode = targetRecordCode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getPlateNumber() {
		return plateNumber;
	}

	public void setPlateNumber(String plateNumber) {
		this.plateNumber = plateNumber;
	}

	public Integer getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(Integer checkResult) {
		this.checkResult = checkResult;
	}

	public String getOperateNum() {
		return operateNum;
	}

	public void setOperateNum(String operateNum) {
		this.operateNum = operateNum;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getCrossName() {
		return crossName;
	}

	public void setCrossName(String crossName) {
		this.crossName = crossName;
	}

	public Integer getOpen() {
		return open;
	}

	public void setOpen(Integer open) {
		this.open = open;
	}
}
