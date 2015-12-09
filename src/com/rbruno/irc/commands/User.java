package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.Server;

public class User extends Command {

	public User() {
		super("USER", 4);
	}

	@Override
	public void execute(Request request) {
		if (request.getConnection().isClient()) {
			request.getConnection().getClient().setUsername(request.getArgs()[0]);
			request.getConnection().getClient().setHostname("irc.rbruno.com");
			request.getConnection().getClient().setServername("RbrunoIRC");
			request.getConnection().getClient().setRealName(request.getArgs()[3]);
			Server.getServer().addClient(request.getConnection().getClient());
		}
		//TODO: Add server to server communication
	}
}
