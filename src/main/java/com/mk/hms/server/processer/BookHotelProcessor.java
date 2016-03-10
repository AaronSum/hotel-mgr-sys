package com.mk.hms.server.processer;

import io.netty.channel.Channel;

import java.io.IOException;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.hms.websocket.HmsWebsocketEndPoint;
import com.mk.synserver.DirectiveData;

public class BookHotelProcessor extends AbstractProcessor {

	private Logger logger = LoggerFactory.getLogger(BookHotelProcessor.class);
	
	@Override
	protected void handle(Channel channel, DirectiveData dd) {
		Long userId = (Long) this.getValue(dd, AbstractProcessor.USER_ID);
		Long orderId = (Long) this.getValue(dd, AbstractProcessor.ORDER_ID);
		JSONObject json = new JSONObject();
		json.put("userId", userId);
		json.put("orderId", orderId);
		json.put("directiveCode", dd.getDirective());
		//WebSocketMessage<String> msg = new TextMessage(json.toString());
		try {
			HmsWebsocketEndPoint.sessionMap.get(userId).getBasicRemote().sendText(json.toString());
		} catch (IOException e) {
			logger.error("websocket握手异常:" + e.getMessage(), e);
		}
	}
}
