package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Request;

public class Admin extends Command {

	public Admin() {
		super("ADMIN", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0)  {
			request.getConnection().send(Reply.RPL_ADMINME, request.getClient(), Server.getServer().getConfig().getProperty("networkname") + " :Administrative info");
			request.getConnection().send(Reply.RPL_ADMINLOC1, request.getClient(), Server.getServer().getConfig().getProperty("networkname") + " :" + Server.getServer().getConfig().getProperty("AdminName"));
			request.getConnection().send(Reply.RPL_ADMINLOC2, request.getClient(), Server.getServer().getConfig().getProperty("networkname") + " :" + Server.getServer().getConfig().getProperty("AdminNick"));
			request.getConnection().send(Reply.RPL_ADMINMAIL, request.getClient(), Server.getServer().getConfig().getProperty("networkname") + " :" + Server.getServer().getConfig().getProperty("AdminEmail"));
		} else {
			//TODO: Server
		}
	}
}
