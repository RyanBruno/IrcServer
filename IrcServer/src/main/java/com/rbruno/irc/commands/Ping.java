package com.rbruno.irc.commands;

import com.rbruno.irc.net.ClientRequest;

public class Ping extends Command {

	public Ping() {
		super("PING", 1);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		request.getConnection().send(":" + getServer(request).getConfig().getProperty("hostname") + " PONG " + request.getClient().getNickname());
	}

}
