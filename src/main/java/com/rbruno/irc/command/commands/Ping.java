package com.rbruno.irc.command.commands;

import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

public class Ping extends Command {

    public Ping(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		return new Response[] {new Response(request.getChannel(), ":" + getContext().getConfig().getHostname() + " PONG <TODO>")};
	}

}
