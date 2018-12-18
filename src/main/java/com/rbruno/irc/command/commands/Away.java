package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Away extends Command {

	public Away(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		Config config = getContext().getConfig();

		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "AWAY", config.getHostname()) };
		}

		Response response[] = new Response[1];

		if (request.getArgs().length == 0 || request.getArgs()[0].equals("")) {
			client.setAwayMessage(Optional.empty());
			response[0] = new Response(Reply.RPL_UNAWAY, client, ":You are no longer marked as being away", config.getHostname());
		} else {
			client.setAwayMessage(Optional.of(request.getArgs()[0]));
			response[0] = new Response(Reply.RPL_NOWAWAY, client, ":You have been marked as being away", config.getHostname());
		}

		return response;
	}

}
