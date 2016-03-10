package com.mk.hms.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mk.hms.exception.SessionTimeOutException;
import com.mk.hms.model.OutModel;
import com.mk.hms.service.MessageService;
import com.mk.hms.view.Page;

/***
 * 推送消息信息控制器
 * @author hdy
 *
 */
@Controller
@RequestMapping("/message")
public class MessageController {
	
	@Autowired
	private MessageService MessageService = null;
	
	/**
	 * 获取消息列表
	 * @return
	 * @throws SessionTimeOutException 
	 */
	@RequestMapping("/messages")
	@ResponseBody
	public OutModel getMessageList(Page page) throws SessionTimeOutException{
		OutModel out = new OutModel(false);
		Map<String, Object> map = new HashMap<String, Object>();
		map = this.getMessageService().getAllMessageList(page);
		out.setSuccess(true);
		out.setAttribute(map);
		return out;
	}
	
	/**
	 * 改变处理标记
	 * @return
	 */
	@RequestMapping("/changeFlag")
	@ResponseBody
	public OutModel changeFlag(long id){
		OutModel out = new OutModel(false);
		this.getMessageService().changeFlag(id);
		out.setSuccess(true);
		return out;
	}

	private MessageService getMessageService() {
		return MessageService;
	}
	
}
