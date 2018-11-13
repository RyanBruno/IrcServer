package com.rbruno.irc.commands.client;

import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Reply;

public class Admin extends ClientCommand {

	public Admin() {
		super("ADMIN", 0);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
			request.getConnection().send(Reply.RPL_ADMINME, request.getClient(), getServer(request).getConfig().getProperty("hostname") + " :Administrative info");
			request.getConnection().send(Reply.RPL_ADMINLOC1, request.getClient(), getServer(request).getConfig().getProperty("hostname") + " :" + getServer(request).getConfig().getProperty("AdminName"));
			request.getConnection().send(Reply.RPL_ADMINLOC2, request.getClient(), getServer(request).getConfig().getProperty("hostname") + " :" + getServer(request).getConfig().getProperty("AdminNick"));
			request.getConnection().send(Reply.RPL_ADMINMAIL, request.getClient(), getServer(request).getConfig().getProperty("hostname") + " :" + getServer(request).getConfig().getProperty("AdminEmail"));
	}
}
