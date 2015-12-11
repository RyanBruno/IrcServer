package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.net.AdjacentServer;
import com.rbruno.irc.Error;

public class ServerCommand extends Command {

	public ServerCommand() {
		super("SERVER", 3);
	}

	@Override
	public void execute(Request request) throws Exception {
		int hopcount = 0;
		hopcount = Integer.parseInt(request.getArgs()[1]);
		AdjacentServer adjacentServer = new AdjacentServer(request.getConnection(), request.getArgs()[0], hopcount, request.getArgs()[2]);
		if (Server.getServer().getServerManager().checkServer(request.getArgs()[0])) {
			request.getConnection().send(Error.ERR_ALREADYREGISTRED, request.getArgs()[0], "You may not reregister");
			//TODO: Log this
		}
		Server.getServer().getServerManager().addServer(adjacentServer);
	}

}
