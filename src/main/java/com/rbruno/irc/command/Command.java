package com.rbruno.irc.command;

import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

public abstract class Command {
	
	private CommandContext context;

	public Command(CommandContext context) {
		this.context = context;
	}

    public abstract Response[] execute(Request request);

	public CommandContext getContext() {
		return context;
	}

}
