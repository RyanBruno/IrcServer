package com.rbruno.irc.commands;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Error;

public class Notice extends Command {

	public Notice() {
		super("NOTICE", 2);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		Client client = getServer(request).getClientManager().getClient(request.getArgs()[0]);
		if (client != null) {
			client.getConnection().send(":" + request.getClient().getAbsoluteName() + " NOTICE " + client.getNickname() + " " + request.getArgs()[1]);
		} else {
			request.getClient().getConnection().send(Error.ERR_NOSUCHNICK, client, request.getArgs()[0] + " :No such nick");
		}
	}
}
