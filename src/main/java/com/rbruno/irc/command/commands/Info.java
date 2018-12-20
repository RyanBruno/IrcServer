package com.rbruno.irc.command.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Info extends Command {

	public Info(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		Config config = getContext().getConfig();

		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "INFO", config.getHostname()) };
		}

		List<Response> responses = new ArrayList<>();
		try {
			BufferedReader inputStream = new BufferedReader(new InputStreamReader(Info.class.getResourceAsStream("/info.txt")));
			inputStream.lines().forEach(l -> responses.add(new Response(Reply.RPL_INFO, client, ":" + l, config.getHostname())));
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return responses.toArray(new Response[0]);
	}

}
