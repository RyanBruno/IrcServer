package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Notice extends Command {

	public Notice() {
		super("NOTICE", 2);
	}

	@Override
	public void execute(Request request) throws Exception {
		Client client = Server.getServer().getClientManager().getClient(request.getArgs()[0]);
		if (client != null) {
			client.getConnection().send(":" + request.getClient().getAbsoluteName() + " NOTICE " + client.getNickname() + " " + request.getArgs()[1]);
		} else {
			request.getClient().getConnection().send(Error.ERR_NOSUCHNICK, client, request.getArgs()[0] + " :No such nick");
		}
	}
}
