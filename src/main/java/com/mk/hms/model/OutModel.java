package com.mk.hms.model;

import java.util.List;

import com.mk.hms.utils.LogUtils;

/**
 * 返回页面所用对象
 * @author hdy
 *
 */
public class OutModel {

	private boolean success; //成功标志
	private String context; //返回文本内容
	private int errorCode; //错误编码
	private String errorMsg; //错误信息
	private boolean system; // 是否是系统提示
	private Object attribute; //自定义属性值
	private List<Object> dataList; //数据列表
		
	// 默认构造，填写成功标志
	public OutModel (boolean success) {
		this.success = success;
	}
	
	public OutModel () {}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		LogUtils.logErr(null, errorMsg, null);
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public List<Object> getDataList() {
		return dataList;
	}

	public void setDataList(List<Object> dataList) {
		this.dataList = dataList;
	}

	public Object getAttribute() {
		return attribute;
	}

	public void setAttribute(Object attribute) {
		this.attribute = attribute;
	}

	public boolean isSystem() {
		return system;
	}

	public void setSystem(boolean system) {
		this.system = system;
	}
	
}
