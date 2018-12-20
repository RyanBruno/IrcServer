package com.rbruno.irc.command.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Version extends Command {

	public Version(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		Config config = getContext().getConfig();

		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "AWAY", config.getHostname()) };
		}

		return new Response[] { new Response(Reply.RPL_VERSION, client, Server.VERSION + " " + config.getHostname(), config.getHostname()) };
	}
}
