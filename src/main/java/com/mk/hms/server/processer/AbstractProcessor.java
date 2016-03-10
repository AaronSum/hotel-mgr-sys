package com.mk.hms.server.processer;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import com.mk.synserver.DirectiveData;

public abstract class AbstractProcessor implements IProcessor {

	public static final String SESSION_ID = "sessionId";

	public static final String HOTEL_ID = "hotelId";
	
	public static final String ORDER_ID = "orderId";
	
	public static final String USER_ID = "userId";
	
	public static final String USER_ID_LIST = "userIdList";

	private static String sessionId = null;

	public void process(Channel channel, DirectiveData dd) {
		this.handle(channel, dd);
	}

	public static String getSessionId() {
		return AbstractProcessor.sessionId;
	}

	protected abstract void handle(Channel channel, DirectiveData dd);

	protected DirectiveData createDirectiveData(int directive) {
		DirectiveData dd = new DirectiveData();
		dd.setDirective(directive);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(AbstractProcessor.SESSION_ID, AbstractProcessor.getSessionId());
		dd.setData(data);

		return dd;
	}

	protected void addData(DirectiveData dd, String key, Object value) {
		dd.getData().put(key, value);
	}

	protected Object getValue(DirectiveData dd, String key) {
		return dd.getData().get(key);
	}

	protected static void setSessionId(String sessionId) {
		AbstractProcessor.sessionId = sessionId;
	}

}
