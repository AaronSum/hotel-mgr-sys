package com.mk.ots.schedule;

import org.quartz.Job;
import org.quartz.impl.JobDetailImpl;

public class JobDetailWrapper extends JobDetailImpl {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	public void setJobClassName(String className) {
		try {
			super.setJobClass((Class<? extends Job>) Class.forName(className));
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
