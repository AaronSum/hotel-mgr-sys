package com.mk.hms.server.processer;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.mk.hms.model.HmsUserModel;
import com.mk.hms.model.InstantMessage;
import com.mk.hms.service.HmsOtsOrderService;
import com.mk.hms.service.HmsUserService;
import com.mk.hms.service.MessageService;
import com.mk.hms.utils.HmsDateUtils;
import com.mk.hms.websocket.HmsWebsocketEndPoint;
import com.mk.synserver.DirectiveData;

public class ReceiveOrderProcessor extends AbstractProcessor {

	private Logger logger = LoggerFactory.getLogger(ReceiveOrderProcessor.class);
	
	@Autowired
	private HmsOtsOrderService hmsOtsOrderService;
	
	@Autowired
	private MessageService messageService;
	
	@Autowired
	private HmsUserService hmsUserService;
	
	@Override
	protected void handle(Channel channel, DirectiveData dd) {
		List<String> userIdList = (List<String>) this.getValue(dd, AbstractProcessor.USER_ID_LIST);
//		Long userId = (Long) this.getValue(dd, AbstractProcessor.USER_ID);
		Long orderId = (Long) this.getValue(dd, AbstractProcessor.ORDER_ID);
		
		String userIdsInStr = "";
		for (String userId : userIdList) {
			if (userIdsInStr.isEmpty()) {
				userIdsInStr = userId;
			} else {
				userIdsInStr = "," + userIdsInStr;
			}
		}
		
		if (userIdsInStr.isEmpty()) {
			return;
		}
		
		List<HmsUserModel> userList = hmsUserService.findHmsUserByIds(userIdsInStr);

		JSONObject json = new JSONObject();
		json.put("orderId", orderId);
		json.put("directiveCode", dd.getDirective());
		for (HmsUserModel user : userList) {
			long userId = user.getId();
			json.put("userId", userId);
			
			try {	
//				HmsOtaOrderModel hmsOtaOrderModel = hmsOtsOrderService.findOtsOrderById(orderId);
				InstantMessage im = new InstantMessage();
				im.setCreateTime(HmsDateUtils.getDateFromString(HmsDateUtils.getDatetime()));
				im.setUpdateTime(HmsDateUtils.getDateFromString(HmsDateUtils.getDatetime()));
				im.setDirective(dd.getDirective());
				im.setTitle("预付订单完成支付提醒");
//				im.setTitle("预付订单完成支付提醒，预抵时间为：" + HmsDateUtils.formatDatetime(hmsOtaOrderModel.getBegintime()));
				im.setData(json.toString());
				im.setUserName(user.getName());
				im.setUserId(userId);
				im.setIsNew(2);
//				im.setHotelId();//愚蠢的表设计，竟然无法在用户表取到酒店id，极其鄙视，希望后期能通过构建字典树弥补
				this.getMessageService().addMessage(im);
				HmsWebsocketEndPoint.sessionMap.get(userId).getBasicRemote().sendText(json.toString());
			} catch (IOException e) {
				logger.error("websocket握手异常:" + e.getMessage() + " userid:" + userId, e);
			} catch (Exception e) {
				logger.error("消息处理异常:" + e.getMessage() + " userid:" + userId, e);
			}
		}
	}

	private MessageService getMessageService() {
		return messageService;
	}
}
