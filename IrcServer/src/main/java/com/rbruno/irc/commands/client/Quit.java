package com.rbruno.irc.commands.client;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;

public class Quit extends ClientCommand {

	public Quit() {
		super("QUIT", 0);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		request.getConnection().close();

		String message = "Leaving";
		if (request.getArgs().length != 0) message = request.getArgs()[0];

		for (Channel current : request.getClient().getChannels()) {
			current.removeClient(request.getClient());
			for (Client client : current.getClients())
				client.getConnection().send(":" + request.getClient().getAbsoluteName() + " QUIT " + message);
		}
		// TODO: Notify all clients and servers

	}

}
