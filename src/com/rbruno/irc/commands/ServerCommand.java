package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.net.AdjacentServer;

public class ServerCommand extends Command {

	public ServerCommand() {
		super("SERVER", 3);
	}

	@Override
	public void execute(Request request) throws Exception{
		int hopcount = 0;
		hopcount = Integer.parseInt(request.getArgs()[1]);
		AdjacentServer adjacentServer = new AdjacentServer(request.getConnection(), request.getArgs()[0], hopcount, request.getArgs()[2]);
		request.getConnection().setAdjacentServer(adjacentServer);
		Server.getServer().getServerManager().addServer(adjacentServer);
		//TODO: check if server is added if so throw ERR_ALREADYREGISTRED
	}

}
