package com.rbruno.irc.command.commands;

import java.util.ArrayList;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Reply;

public class List extends ClientCommand {

	public List() {
		super("LIST", 0);
	}

	@Override
	public void execute(ClientRequest request) {
		if (request.getArgs().length == 0) {
			ArrayList<Channel> channels = getServer(request).getChannelManger().getChannels();
			request.getConnection().send(Reply.RPL_LISTSTART, request.getClient(), "Channel :Users Name");
			for (Channel current : channels) {
				request.getConnection().send(Reply.RPL_LIST, request.getClient(), current.getName() + " " + current.getUsersCount() + " :" + current.getTopic());
			}
			request.getConnection().send(Reply.RPL_LISTEND, request.getClient(), ":End of /LIST");
		} else {
			String[] stringChannels = request.getArgs()[0].split(",");
			request.getConnection().send(Reply.RPL_LISTSTART, request.getClient(), "Channel :Users  Name");
			for (String current : stringChannels) {
				Channel channel = getServer(request).getChannelManger().getChannel(current);
				request.getConnection().send(Reply.RPL_LIST, request.getClient(), channel.getName() + " " + channel.getUsersCount() + " :" + channel.getTopic());
			}
			request.getConnection().send(Reply.RPL_LISTEND, request.getClient(), " :End of /LIST");
		}
	}

}
