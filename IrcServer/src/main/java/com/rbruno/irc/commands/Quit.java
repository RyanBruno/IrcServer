package com.rbruno.irc.commands;

import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Quit extends Command {

	public Quit() {
		super("QUIT", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getConnection().isClient()) {
			request.getConnection().close();

			String message = "Leaving";
			if (request.getArgs().length != 0) message = request.getArgs()[0];

			for (Channel current : request.getClient().getChannels()) {
				current.removeClient(request.getClient());
				for (Client client : current.getClients())
					client.getConnection().send(":" + request.getClient().getAbsoluteName() + " QUIT " + message);
			}
			// TODO: Notify all clients and servers
		} else {
			// TODO: netsplits
		}
	}

}
