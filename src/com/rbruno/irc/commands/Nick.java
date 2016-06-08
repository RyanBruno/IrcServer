package com.rbruno.irc.commands;

import java.io.IOException;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Nick extends Command {

	public Nick() {
		super("NICK", 1);
	}

	@Override
	public void execute(Request request) throws IOException {
		if (Server.getServer().getClientManager().isNick(request.getArgs()[0])) {
			request.getConnection().send(Error.ERR_NICKNAMEINUSE, request.getClient(), request.getArgs()[0] + " :Nickname is already in use");
			request.getConnection().close();
			return;
		}
		request.getConnection().setClient(new Client(request.getConnection(), request.getArgs()[0]));
	}

}
