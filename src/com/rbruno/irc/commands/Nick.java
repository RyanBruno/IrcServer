package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.net.Client;

public class Nick extends Command {

	public Nick() {
		super("NICK", 1);
	}

	@Override
	public void execute(Request request) {
		request.getConnection().setClient(new Client(request.getConnection(), request.getArgs()[0]));
	}

}
