package com.rbruno.irc.commands.registration;

import com.rbruno.irc.net.Request;

public class Pass extends RegCommand {

	public Pass() {
		super("PASS", 1);
	}

	@Override
	public void execute(Request request) {
		request.getConnection().setConnectionPassword(request.getArgs()[0]);		
	}

}
