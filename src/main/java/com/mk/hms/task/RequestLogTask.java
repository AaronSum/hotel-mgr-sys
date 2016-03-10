package com.mk.hms.task;

import com.mk.hms.model.AopLogWithBLOBs;
import com.mk.hms.utils.LogUtils;
import com.mk.hms.utils.ThreadTask;

/**
 * 请求日志操作
 * @author hdy
 *
 */
public class RequestLogTask extends ThreadTask{

	private AopLogWithBLOBs aoplog;
	
	public RequestLogTask(AopLogWithBLOBs aoplog) {
		this.aoplog = aoplog;
	}
	
	@Override
	public void execute() {
		LogUtils.getRequestLogService().add(aoplog);
	}

}
