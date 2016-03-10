package com.mk.hms.utils;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SetisThresholdJob extends QuartzJobBean {
	private int timeout;

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		SynThesholdByJob.copy();
	}

}
