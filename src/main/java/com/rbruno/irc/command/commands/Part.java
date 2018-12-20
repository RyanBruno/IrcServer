package com.rbruno.irc.command.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Error;

public class Part extends Command {

	public Part(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		Config config = getContext().getConfig();

		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "PART", config.getHostname()) };
		}

		List<Response> response = new ArrayList<Response>();

		for (String name : request.getArgs()[0].split(",")) {
			Channel channel = getContext().getChannel(name);

			if (channel == null) {
				response.add(new Response(Error.ERR_NOSUCHCHANNEL, client, name + " :No such channel", config.getHostname()));
				continue;
			}

			if (!channel.hasClient(client)) {
				response.add(new Response(Error.ERR_NOTONCHANNEL, client, channel.getName() + " :You're not on that channel", config.getHostname()));
				continue;
			}

			Iterator<Client> it = channel.getIterator();
			while (it.hasNext()) {
				Client current = it.next();
				response.add(new Response(current.getChannel(), ":" + client.getAbsoluteName() + " Part " + channel.getName()));
			}

			channel.removeClient(client);

		}

		return response.toArray(new Response[0]);
	}

}
