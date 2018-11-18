package com.rbruno.irc.command.commands;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;

public class Part extends Command {

	public Part() {
		super("PART", 1);
	}

	@Override
	public void execute(Request request, Client client) {
		String[] channels = request.getArgs()[0].split(",");
		for (String channelName : channels) {
			Channel channel = getServer(request).getChannelManger().getChannel(channelName);
			String message = "Leaving";
			if (request.getArgs().length != 0)
				message = request.getArgs()[0];
			for (LocalClient client : channel.getClients())
				client.getConnection().send(":" + request.getClient().getAbsoluteName() + " PART " + message);
			channel.removeClient(request.getClient());
			request.getClient().removeChannel(channel);


		}

	}

}
