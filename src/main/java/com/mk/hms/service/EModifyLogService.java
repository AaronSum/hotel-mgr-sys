package com.mk.hms.service;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mk.hms.mapper.EModifyLogMapper;
import com.mk.hms.model.EModifyLog;

@Service
@Transactional
public class EModifyLogService {
	private static Logger logger = Logger.getLogger(EModifyLogService.class);
    @Autowired
    private EModifyLogMapper mapper;
    /**
     * 增加一条日志纪录
     * @param record
     * @return
     */
    public int addLog(EModifyLog record){
    		return mapper.insert(record);
    }
    
    public void outLog(EModifyLog record){
    	    logger.info(record);
   }
    
}
