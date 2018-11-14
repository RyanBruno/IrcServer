package com.rbruno.irc.command.commands;

import com.rbruno.irc.net.ClientRequest;

public class Ping extends ClientCommand {

	public Ping() {
		super("PING", 1);
	}

	@Override
	public void execute(ClientRequest request) {
		request.getConnection().send(":" + getServer(request).getConfig().getProperty("hostname") + " PONG " + request.getClient().getNickname());
	}

}
