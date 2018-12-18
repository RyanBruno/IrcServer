package com.rbruno.irc.command.commands;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Admin extends Command {

	public Admin(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		Config config = getContext().getConfig();

		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "ADMIN", config.getHostname()) };
		}

		Response response[] = new Response[4];

		response[0] = new Response(Reply.RPL_ADMINME, client, config.getHostname() + " :Administrative info", config.getHostname());

		response[1] = new Response(Reply.RPL_ADMINLOC1, client, config.getHostname() + " :" + config.getAdminLoc1(), config.getHostname());

		response[2] = new Response(Reply.RPL_ADMINLOC2, client, config.getHostname() + " :" + config.getAdminLoc2(), config.getHostname());

		response[3] = new Response(Reply.RPL_ADMINMAIL, client, config.getHostname() + " :" + config.getAdminMail(), config.getHostname());
		return response;
	}
}
