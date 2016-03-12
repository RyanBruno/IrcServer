package com.rbruno.irc.commands;

import com.rbruno.irc.Reply;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;

public class Admin extends Command {

	public Admin() {
		super("ADMIN", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0)  {
			request.getConnection().send(Reply.RPL_ADMINME, request.getClient(), Server.getServer().getConfig().getProperty("ServerName") + " :Administrative info");
			request.getConnection().send(Reply.RPL_ADMINLOC1, request.getClient(), Server.getServer().getConfig().getProperty("ServerName") + " :" + Server.getServer().getConfig().getProperty("AdminName"));
			request.getConnection().send(Reply.RPL_ADMINLOC2, request.getClient(), Server.getServer().getConfig().getProperty("ServerName") + " :" + Server.getServer().getConfig().getProperty("AdminNick"));
			request.getConnection().send(Reply.RPL_ADMINMAIL, request.getClient(), Server.getServer().getConfig().getProperty("ServerName") + " :" + Server.getServer().getConfig().getProperty("AdminEmail"));
		} else {
			//TODO: Server
		}
	}
}
