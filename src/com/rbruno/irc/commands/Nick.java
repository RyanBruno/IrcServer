package com.rbruno.irc.commands;

import java.io.IOException;

import com.rbruno.irc.Error;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.net.Client;

public class Nick extends Command {

	public Nick() {
		super("NICK", 1);
	}

	@Override
	public void execute(Request request) throws IOException {
		if (Server.getServer().getClientManager().isNick(request.getConnection().getClient().getNickname())) {
			request.getConnection().send(Error.ERR_NICKNAMEINUSE, request.getClient(), request.getClient().getNickname() + " :Nickname is already in use");
			request.getConnection().close();
			return;
		}
		request.getConnection().setClient(new Client(request.getConnection(), request.getArgs()[0]));
	}

}
