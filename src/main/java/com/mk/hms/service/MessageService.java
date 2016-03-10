package com.mk.hms.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.mapper.InstantMessageMapper;
import com.mk.hms.model.InstantMessage;
import com.mk.hms.model.InstantMessageCriteria;
import com.mk.hms.utils.SessionUtils;
import com.mk.hms.view.Page;

/**
 * 消息推送 service
 * @author hdy
 *
 */
@Service
@Transactional
public class MessageService {

	@Autowired
	private InstantMessageMapper instantMessageMapper = null;
	
	/**
	 * 获取消息列表
	 * @return
	 * @throws SessionTimeOutException 
	 */
	public Map<String, Object> getAllMessageList(Page page) throws SessionTimeOutException {
		Map<String, Object> outMap = new HashMap<String, Object>();
		int total = page.getTotal();
		Long userId = SessionUtils.getSessionLoginUser().getUser().getId();
		InstantMessageCriteria instantMessageCriteria = new InstantMessageCriteria();
		instantMessageCriteria.createCriteria().andUserIdEqualTo(userId);
		if (total == 0) {
			total = this.getInstantMessageMapper().countByExample(instantMessageCriteria);
		}
		instantMessageCriteria.setOrderByClause("createtime desc");
		instantMessageCriteria.setLimitStart(page.getStartIndex());
		instantMessageCriteria.setLimitEnd(page.getPageSize());
		outMap.put("rows", this.getInstantMessageMapper().selectByExample(instantMessageCriteria));
		outMap.put("total", total);
		return outMap;
	}

	/**
	 * 添加消息
	 * @param im 消息
	 */
	public void addMessage(InstantMessage im) {
		this.getInstantMessageMapper().insert(im);
	}
	
	/**
	 * 改变处理标记
	 */
	public void changeFlag(long id) {
		InstantMessage im = new InstantMessage();
		im.setId(id);
		im.setIsNew(2);
		this.getInstantMessageMapper().updateByPrimaryKey(im); 
	}
	
	private InstantMessageMapper getInstantMessageMapper() {
		return instantMessageMapper;
	}

}
