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
		System.out.println(request.getConnection().getType());
		switch (request.getConnection().getType()) {
		case CLIENT:
			//TODO Error
			break;
		case LOGGIN_IN:
			System.out.println("Loggin");
			Client client = new Client(request.getConnection(), request.getPrefix(), request.getArgs()[0], request.getArgs()[1], request.getArgs()[2], request.getArgs()[3]);
			Server.getServer().getClientManager().addClient(client);
			request.getConnection().setClient(client);
			break;
		case SERVER:
			//TODO Server
			break;
		}
		
	}
}
