package com.rbruno.irc.commands;

import java.util.ArrayList;

import com.rbruno.irc.Channel;
import com.rbruno.irc.Reply;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.net.Client;

public class Names extends Command {

	public Names() {
		super("NAMES", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0) {
			ArrayList<Channel> channels = Server.getServer().getChannelManger().getChannels();
			for(Channel current : channels) {
				String message = current.getName() + ":";
				ArrayList<Client> clients = current.getClients();
				for(Client client : clients) {
					message = message + " " + client.getNickname();
				}
				request.getConnection().send(Reply.RPL_NAMREPLY, request.getClient(), message);
			}
		} else {
			String[] stringChannels = request.getArgs()[0].split(",");
			for(String current : stringChannels) {
				Channel channel = Server.getServer().getChannelManger().getChannel(current);
				String message = channel.getName() + ":";
				ArrayList<Client> clients = channel.getClients();
				for(Client client : clients) {
					message = message + " " + client.getNickname();
				}
				request.getConnection().send(Reply.RPL_NAMREPLY, request.getClient(), message);
			}
		}
	}

}
