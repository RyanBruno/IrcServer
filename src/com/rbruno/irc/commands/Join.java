package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.Server;

public class Join extends Command {

	public Join() {
		super("JOIN", 1);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getConnection().isClient()) {
			// TODO: Check if can join channel
			// TODO: Check if trying to join multiple channels
			request.getConnection().getClient().addChannels(request.getArgs()[0]);
		} else {
			Server.getServer().getClientManager().getClient(request.getPrefix()).addChannels(request.getArgs()[0]);
		}
	}

}
