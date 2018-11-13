package com.rbruno.irc.commands.client;

import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Reply;

public class Away extends ClientCommand {

	public Away() {
		super("AWAY", 0);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		String message = "";
		if (request.getArgs().length != 0) message = request.getArgs()[0];
		if (message.startsWith(":")) message = message.substring(1);
		request.getClient().setAwayMessage(message);
		if (request.getClient().getAwayMessage().equals("")) {
			request.getConnection().send(Reply.RPL_UNAWAY, request.getClient(), ":You are no longer marked as being away");
		} else {
			request.getConnection().send(Reply.RPL_NOWAWAY, request.getClient(), ":You have been marked as being away");
		}
	}
}
