package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.Error;
import com.rbruno.irc.net.AdjacentServer;

public class SQuit extends Command {

	public SQuit() {
		super("SQUIT", 0);
	}
	
	@Override
	public void execute(Request request) throws Exception {
		if (Server.getServer().getServerManager().checkServer(request.getArgs()[0])){
			Server.getServer().getServerManager().removeServer(request.getArgs()[0]);
			AdjacentServer[] servers = Server.getServer().getServerManager().getAdjacentServers();
			for (AdjacentServer current : servers) {
				if (!current.getName().equals(request.getArgs()[0])) {
					//TODO Send SQUIT message
				}
			}
		} else {
			request.getConnection().send(Error.ERR_NOSUCHSERVER, request.getArgs()[0], "No such server");
		}
	}

}
