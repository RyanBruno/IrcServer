package com.rbruno.irc.commands.registration;

import java.io.IOException;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientConnection;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class User extends RegCommand {

	public User() {
		super("USER", 4);
	}

	@Override
	public void execute(Request request) throws IOException {
		Client client = new Client(request.getConnection().getNickname());
		client.setConnection(new ClientConnection(request.getConnection().getSocket(), client, getServer(request), request.getConnection().getReader()));

		client.setUsername(request.getArgs()[0]);
		client.setHostname(getServer(request).getConfig().getProperty("hostname"));
		client.setServername(getServer(request).getConfig().getProperty("hostname"));
		client.setRealName(request.getArgs()[3]);
		client.getConnection().send(1, client.getNickname(), ":Welcome to the " + getServer(request).getConfig().getProperty("hostname") + " Internet Relay Chat Network " + client.getNickname());
		client.getConnection().send(Reply.RPL_LUSERCLIENT, client, ":There are " + getServer(request).getClientManager().getClientCount() + " users and " + getServer(request).getClientManager().getInvisibleClientCount() + " invisible on 1 servers");
		client.getConnection().send(Reply.RPL_LUSEROP, client, getServer(request).getClientManager().getOps() + " :operator(s) online");
		client.getConnection().send(Reply.RPL_LUSERCHANNELS, client, getServer(request).getChannelManger().getNonSecretChannels() + " :channels formed");
		client.getConnection().send(Reply.RPL_LUSERME, client, ":I have " + getServer(request).getClientManager().getClientCount() + " clients and 1 servers");
		getServer(request).sendMOTD(client);
		getServer(request).getClientManager().addClient(client);

		client.getConnection().run();

	}
}
