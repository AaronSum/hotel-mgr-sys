package com.mk.hms.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import com.mk.synserver.DirectiveData;

public class ServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ServerChannel.setChannel(ctx.channel());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DirectiveData dd = (DirectiveData) msg;
		DirectiveProcessFactory.process(dd, ctx.channel());
	}

}
