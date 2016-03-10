package com.mk.hms.server;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import com.mk.hms.server.processer.AbstractProcessor;
import com.mk.synserver.DirectiveData;

public class ServerChannel {

	private static Channel channel = null;

	public static void setChannel(Channel channel) {
		ServerChannel.channel = channel;
	}

	public static Channel getChannel() {
		return ServerChannel.channel;
	}

	public static void checkHotelOrder(Long userId) {
		DirectiveData dd = new DirectiveData();
		dd.setDirective(DirectiveSet.DIRECTIVE_REGISTER_HOTEL);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put(AbstractProcessor.SESSION_ID, AbstractProcessor.getSessionId());
		data.put(AbstractProcessor.USER_ID, userId);
		dd.setData(data);

		ServerChannel.getChannel().writeAndFlush(dd);
	}

}
