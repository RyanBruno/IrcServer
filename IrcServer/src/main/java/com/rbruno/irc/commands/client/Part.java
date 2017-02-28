package com.rbruno.irc.commands.client;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;

public class Part extends ClientCommand {

	public Part() {
		super("PART", 1);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		String[] channels = request.getArgs()[0].split(",");
		for (String channelName : channels) {
			Channel channel = getServer(request).getChannelManger().getChannel(channelName);
			String message = "Leaving";
			if (request.getArgs().length != 0)
				message = request.getArgs()[0];
			for (Client client : channel.getClients())
				client.getConnection().send(":" + request.getClient().getAbsoluteName() + " PART " + message);
			channel.removeClient(request.getClient());
			request.getClient().removeChannel(channel);


		}

	}

}
