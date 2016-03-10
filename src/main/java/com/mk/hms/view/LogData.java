package com.mk.hms.view;


/**
 * 日志数据明细对象
 * @author hdy
 *
 */
public class LogData {

	private Object oldData;
	private Object newData;
	private String time;
	private String memo;

	public LogData() {}
	
	
	public LogData(Object oldData, Object newData, String time, String memo) {
		this.oldData = oldData;
		this.newData = newData;
		this.time = time;
		this.memo = memo;
	}
	
	public Object getOldData() {
		return oldData;
	}

	public void setOldData(Object oldData) {
		this.oldData = oldData;
	}

	public Object getNewData() {
		return newData;
	}

	public void setNewData(Object newData) {
		this.newData = newData;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
