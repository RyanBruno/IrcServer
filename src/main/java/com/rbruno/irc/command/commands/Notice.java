package com.rbruno.irc.command.commands;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class Notice extends Command {

	public Notice() {
		super("NOTICE", 2);
	}

	@Override
	public void execute(Request request, Client client) {
		LocalClient client = getServer(request).getClientManager().getClient(request.getArgs()[0]);
		if (client != null) {
			client.getConnection().send(":" + request.getClient().getAbsoluteName() + " NOTICE " + client.getNickname() + " " + request.getArgs()[1]);
		} else {
			request.getClient().getConnection().send(Error.ERR_NOSUCHNICK, client, request.getArgs()[0] + " :No such nick");
		}
	}
}
