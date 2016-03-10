package com.mk.hms.server;

import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.Map;

import com.mk.hms.server.processer.BookHotelProcessor;
import com.mk.hms.server.processer.IProcessor;
import com.mk.hms.server.processer.ReceiveOrderProcessor;
import com.mk.hms.server.processer.RequestIdentiferProcessor;
import com.mk.synserver.DirectiveData;

/**
 * 指令处理工厂.
 *
 * @author zhaoshb
 *
 */
public class DirectiveProcessFactory {

	private static Map<Integer, IProcessor> processorMap = null;

	static {
		DirectiveProcessFactory.processorMap = new HashMap<Integer, IProcessor>();
		DirectiveProcessFactory.processorMap.put(DirectiveSet.DIRECTIVE_BOOK_HOTEL, new BookHotelProcessor());
		DirectiveProcessFactory.processorMap.put(DirectiveSet.DIRECTIVE_RECEIVE_ORDER, new ReceiveOrderProcessor());
		DirectiveProcessFactory.processorMap.put(DirectiveSet.DIRECTIVE_REQUEST_IDENTIER, new RequestIdentiferProcessor());
	}

	public static IProcessor getProcess(int directive) {
		return DirectiveProcessFactory.processorMap.get(Integer.valueOf(directive));
	}

	public static void process(int directive, Channel channel) {
		DirectiveProcessFactory.getProcess(directive).process(channel, null);
	}

	public static void process(DirectiveData directiveData, Channel channel) {
		DirectiveProcessFactory.getProcess(directiveData.getDirective()).process(channel, directiveData);
	}

}
