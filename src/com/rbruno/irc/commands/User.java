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
		switch (request.getConnection().getType()) {
		case LOGGIN_IN:
			//TODO Error Must send nick 1st
			break;
		case CLIENT:
			Client client = request.getClient();
			client.setUsername(request.getArgs()[0]);
			client.setHostname(request.getArgs()[1]);
			client.setServername(request.getArgs()[2]);
			client.setRealName(request.getArgs()[3]);
			Server.getServer().getClientManager().addClient(client);
			request.getConnection().setClient(client);
			break;
		case SERVER:
			//TODO Server
			break;
		}
		
	}
}
