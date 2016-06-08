package com.rbruno.irc.commands;

import com.rbruno.irc.templates.Request;

public class Quit extends Command {

	public Quit() {
		super("QUIT", 0);
	}
	
	@Override
	public void execute(Request request) throws Exception {
		if (request.getConnection().isClient()) {
			request.getConnection().close();
			//TODO: Notify all clients and servers
		} else {
			//TODO: netsplits
		}
	}

}
