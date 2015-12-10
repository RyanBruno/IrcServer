package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.net.Client;

public class User extends Command {

	public User() {
		super("USER", 4);
	}

	@Override
	public void execute(Request request) {
		if (request.getConnection().isClient()) {
			request.getConnection().getClient().setUsername(request.getArgs()[0]);
			request.getConnection().getClient().setHostname(Server.getServer().getConfig().getProperty("hostname"));
			request.getConnection().getClient().setServername(Server.getServer().getConfig().getProperty("servername"));
			request.getConnection().getClient().setRealName(request.getArgs()[3]);
			Server.getServer().getClientManager().addClient(request.getConnection().getClient());
		} else {
			Client client = new Client(request.getConnection(), request.getPrefix(), request.getArgs()[0], request.getArgs()[1], request.getArgs()[2], request.getArgs()[3]);
			Server.getServer().getClientManager().addClient(client);
		}
	}
}
