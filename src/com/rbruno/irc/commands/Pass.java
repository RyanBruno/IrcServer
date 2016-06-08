package com.rbruno.irc.commands;

import com.rbruno.irc.templates.Request;

public class Pass extends Command {

	public Pass() {
		super("PASS", 1);
	}

	@Override
	public void execute(Request request) {
		request.getConnection().setConnectionPassword(request.getArgs()[0]);		
	}

}
