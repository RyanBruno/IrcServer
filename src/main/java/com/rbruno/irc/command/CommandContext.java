package com.rbruno.irc.command;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

import io.netty.channel.Channel;

public class CommandContext {
	
	private CommandInvoker commandInvoker;

	public CommandContext() {
		this.commandInvoker = new CommandInvoker(this);
	}
	
	public Response[] invoker(Request request) {
		return commandInvoker.runCommand(request);
	}

	public Config getConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	public Client getClient(Channel channel) {
		// TODO Auto-generated method stub
		return null;
	}

}
