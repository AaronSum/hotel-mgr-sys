package com.mk.hms.websocket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.hms.server.ServerChannel;

/**
 * websocket
 *
 * @author hdy
 *
 */
@ServerEndpoint("/websocket")
public class HmsWebsocketEndPoint {

	private final String socketOpenDirective = "connect";

	private static String DIRECTIVE_PING = "ping";

	public static Map<Long, Session> sessionMap = null;

	private static Logger logger = LoggerFactory.getLogger(HmsWebsocketEndPoint.class);

	static {
		HmsWebsocketEndPoint.sessionMap = new ConcurrentHashMap<Long, Session>();
	}

	@OnOpen
	public void onOpen(Session session, EndpointConfig config) {
		HmsWebsocketEndPoint.logger.info("websocket open");
	}

	@OnClose
	public void onClose(Session session) {
		HmsWebsocketEndPoint.logger.info("websocket close");
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		// session.getBasicRemote().sendText(msg);
		JSONObject data = JSONObject.fromObject(msg);
		String directive = data.getString("directive");
		if (this.socketOpenDirective.equals(directive)) {
			Long userId = data.getLong("userId");
			HmsWebsocketEndPoint.sessionMap.put(userId, session);
			ServerChannel.checkHotelOrder(userId);
		} else if (HmsWebsocketEndPoint.DIRECTIVE_PING.equals(directive)) {
			HmsWebsocketEndPoint.logger.info("websocket ping");
		}
	}
}
