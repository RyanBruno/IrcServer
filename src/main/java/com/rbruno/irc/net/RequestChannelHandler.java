package com.rbruno.irc.net;

import com.rbruno.irc.command.CommandInvoker;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class RequestChannelHandler extends SimpleChannelInboundHandler<String> {

	private CommandInvoker invoker;

	public RequestChannelHandler(CommandInvoker invoker) {
		this.invoker = invoker;
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		Request request = new Request(ctx.channel(), msg);

		Response[] response = invoker.runCommand(request);

		for (Response current : response) {
			current.getChannel().writeAndFlush(current.getMessage());
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
