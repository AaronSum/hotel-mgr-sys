package com.mk.hms.server.processer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import com.mk.hms.server.DirectiveSet;
import com.mk.synserver.DirectiveData;

/**
 * 接受同步服务器身份认证处理.
 *
 * @author zhaoshb
 *
 */
public class RequestIdentiferProcessor extends AbstractProcessor {

	private static final String INENTIFER = "identifer";

	private static final String SERVER = "hms";

	@Override
	protected void handle(Channel channel, DirectiveData dd) {
		this.setGloablSessionId(dd);
		ChannelFuture future = channel.writeAndFlush(this.createRequestIdentiferDD());
	}

	private void setGloablSessionId(DirectiveData dd) {
		String sessionId = (String) this.getValue(dd, AbstractProcessor.SESSION_ID);
		AbstractProcessor.setSessionId(sessionId);
	}

	private DirectiveData createRequestIdentiferDD() {
		DirectiveData dd = this.createDirectiveData(DirectiveSet.DIRECTIVE_SEND_IDENTIFER);
		this.addData(dd, RequestIdentiferProcessor.INENTIFER, RequestIdentiferProcessor.SERVER);

		return dd;
	}


}
