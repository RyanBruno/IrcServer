package com.rbruno.irc.commands;

import java.util.ArrayList;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Reply;

public class Names extends Command {

	public Names() {
		super("NAMES", 0);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		if (request.getArgs().length == 0) {
			ArrayList<Channel> channels = getServer(request).getChannelManger().getChannels();
			for (Channel current : channels) {
				String message = current.getName() + " :";
				ArrayList<Client> clients = current.getClients();
				for (Client client : clients) {
					if (current.checkOP(client) || client.getModes().contains('o')) {
						message = message + "@" + client.getNickname() + " ";
					} else {
						message = message + "+" + client.getNickname() + " ";
					}
				}
				request.getConnection().send(Reply.RPL_NAMREPLY, request.getClient(), message);
			}
		} else {
			String[] stringChannels = request.getArgs()[0].split(",");
			for (String current : stringChannels) {
				Channel channel = getServer(request).getChannelManger().getChannel(current);
				String message = channel.getName() + " :";
				ArrayList<Client> clients = channel.getClients();
				for (Client client : clients) {
					if (channel.checkOP(client) || client.getModes().contains('o')) {
						message = message + "@" + client.getNickname() + " ";
					} else {
						message = message + "+" + client.getNickname() + " ";
					}
				}
				request.getConnection().send(Reply.RPL_NAMREPLY, request.getClient(), message);
			}
		}
	}

}
