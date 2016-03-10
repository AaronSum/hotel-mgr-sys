package com.mk.hms.server;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:synserver.properties")
public class HmsServer implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(HmsServer.class);

	@Value("${synserver.ip}")
	private String ip = null;

	@Value("${synserver.port}")
	private Integer port = null;

	@Override
	public void afterPropertiesSet() {
		new Thread(new StartTask(this.getIp(), this.getPort())).start();
	}

	public void run(String ip, Integer port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group);
			bootstrap.channel(NioSocketChannel.class);
			bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
			bootstrap.handler(new InnerChannelHandler());

			InetSocketAddress socketAddr = new InetSocketAddress(ip, port);
			ChannelFuture channelFuture = bootstrap.connect(socketAddr).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			HmsServer.logger.error("can not connect synserver {}:{}", ip, port);
		} finally {
			group.shutdownGracefully();
			this.reconnect(ip, port);
		}
	}

	private void reconnect(String ip, Integer port) {
		try {
			TimeUnit.SECONDS.sleep(5);
			new Thread(new StartTask(ip, port)).start();
		} catch (InterruptedException e) {
			HmsServer.logger.error(e.getMessage(), e);
		}

	}

	private String getIp() {
		return this.ip;
	}

	private Integer getPort() {
		return this.port;
	}

	private class InnerChannelHandler extends ChannelInitializer<SocketChannel> {

		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ChannelPipeline pipeline = ch.pipeline();
			pipeline.addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(null)));
			pipeline.addLast(new ObjectEncoder());

			pipeline.addLast(new ServerHandler());
		}
	}

	private class StartTask implements Runnable {

		private String ip = null;

		private Integer port = null;

		public StartTask(String ip, Integer port) {
			this.ip = ip;
			this.port = port;
		}

		@Override
		public void run() {
			HmsServer.this.run(this.getIp(), this.getPort());
		}

		public String getIp() {
			return this.ip;
		}

		public Integer getPort() {
			return this.port;
		}

	}
}
