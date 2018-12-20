package com.rbruno.irc.command.commands;

import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

public class Pass extends Command {

	public Pass(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		// TODO
		return null;
	}

}
