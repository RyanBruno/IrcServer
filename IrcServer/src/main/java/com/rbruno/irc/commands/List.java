package com.rbruno.irc.commands;

import java.util.ArrayList;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Request;

public class List extends Command {

	public List() {
		super("LIST", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0) {
			ArrayList<Channel> channels = Server.getServer().getChannelManger().getChannels();
			request.getConnection().send(Reply.RPL_LISTSTART, request.getClient(), "Channel :Users Name");
			for (Channel current : channels) {
				request.getConnection().send(Reply.RPL_LIST, request.getClient(), current.getName() + " " + current.getUsersCount() + " :" + current.getTopic());
			}
			request.getConnection().send(Reply.RPL_LISTEND, request.getClient(), ":End of /LIST");
		} else {
			String[] stringChannels = request.getArgs()[0].split(",");
			request.getConnection().send(Reply.RPL_LISTSTART, request.getClient(), "Channel :Users  Name");
			for (String current : stringChannels) {
				Channel channel = Server.getServer().getChannelManger().getChannel(current);
				request.getConnection().send(Reply.RPL_LIST, request.getClient(), channel.getName() + " " + channel.getUsersCount() + " :" + channel.getTopic());
			}
			request.getConnection().send(Reply.RPL_LISTEND, request.getClient(), " :End of /LIST");
		}
	}

}
