package com.rbruno.irc.commands;

import com.rbruno.irc.Reply;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;

public class Version extends Command {

	public Version() {
		super("VERSION", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0) {
			request.getConnection().send(Reply.RPL_VERSION, request.getClient(), Server.getVersion() + " " + Server.getServer().getConfig().getProperty("ServerName"));
		} else {
			//TODO: Server
		}
	}

}
