package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Admin extends Command {

	public Admin() {
		super("ADMIN", 0);
	}

	@Override
	public void execute(Request request, Optional<Client> client) {
	  super.execute(request, client);
			request.getConnection().send(Reply.RPL_ADMINME, client.get(), getServer(request).getConfig().getProperty("hostname") + " :Administrative info");
			request.getConnection().send(Reply.RPL_ADMINLOC1, client.get(), getServer(request).getConfig().getProperty("hostname") + " :" + getServer(request).getConfig().getProperty("AdminName"));
			request.getConnection().send(Reply.RPL_ADMINLOC2, client.get(), getServer(request).getConfig().getProperty("hostname") + " :" + getServer(request).getConfig().getProperty("AdminNick"));
			request.getConnection().send(Reply.RPL_ADMINMAIL, client.get(), getServer(request).getConfig().getProperty("hostname") + " :" + getServer(request).getConfig().getProperty("AdminEmail"));
	}
}
