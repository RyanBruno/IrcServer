package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.templates.Request;

public class Ping extends Command {

	public Ping() {
		super("PING", 1);
	}
	
	@Override
	public void execute(Request request) throws Exception {
		switch (request.getConnection().getType()){
		case CLIENT:
			request.getConnection().send(":" + Server.getServer().getConfig().getProperty("hostname") + " PONG " + request.getClient().getNickname());
			break;
		case LOGGIN_IN:
			break;
		case SERVER:
			//TODO Server
			break;
		}
	}

}
