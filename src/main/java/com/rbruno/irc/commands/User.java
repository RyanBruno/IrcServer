package com.rbruno.irc.commands;

import java.io.IOException;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class User extends Command {

	public User() {
		super("USER", 4);
	}

	@Override
	public void execute(Request request) throws IOException {
		switch (request.getConnection().getType()) {
		case LOGGIN_IN:
			// TODO Error Must send nick 1st
			break;
		case CLIENT:
			Client client = request.getClient();
			client.setUsername(request.getArgs()[0]);
			client.setHostname(Server.getServer().getConfig().getProperty("hostname"));
			client.setServername(Server.getServer().getConfig().getProperty("hostname"));
			client.setRealName(request.getArgs()[3].substring(1));
			Server.getServer().getClientManager().addClient(client);
			request.getConnection().setClient(client);
			request.getConnection().send(1, client.getNickname(), ":Welcome to the " + Server.getServer().getConfig().getProperty("networkname") + " Internet Relay Chat Network " + client.getNickname());
			request.getConnection().send(Reply.RPL_LUSERCLIENT, client, ":There are " + Server.getServer().getClientManager().getUserCount() + " users and " + Server.getServer().getClientManager().getInvisibleUserCount() + " invisible on 1 servers");
			request.getConnection().send(Reply.RPL_LUSEROP, client, Server.getServer().getClientManager().getOps() + " :operator(s) online");
			request.getConnection().send(Reply.RPL_LUSERUNKNOWN, client, "0 :unknown connection(s)");
			request.getConnection().send(Reply.RPL_LUSERCHANNELS, client, "0 :channels formed");
			request.getConnection().send(Reply.RPL_LUSERME, client, ":I have " + Server.getServer().getClientManager().getUserCount() + " clients and 1 servers");
			Server.getServer().sendMOTD(client);
			break;
		case SERVER:
			// TODO Server
			break;
		}

	}
}
