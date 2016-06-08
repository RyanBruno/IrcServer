package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Request;

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
