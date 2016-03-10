package com.mk.hms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.mapper.AopLogMapper;
import com.mk.hms.model.AopLogWithBLOBs;

/**
 * 日志处理
 * @author hdy
 *
 */
@Service
@Transactional
public class RequestLogService {

	@Autowired
	private AopLogMapper aopLogMapper = null;
	
	/**
	 * 添加日志
	 * @param map 日志对象
	 */
	public void add(AopLogWithBLOBs aoplog) {
		this.getAopLogMapper().insert(aoplog);
	}

	private AopLogMapper getAopLogMapper() {
		return aopLogMapper;
	}

}
