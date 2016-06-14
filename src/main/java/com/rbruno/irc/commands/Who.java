package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Who extends Command {

	public Who() {
		super("WHO", 1);
	}

	@Override
	public void execute(Request request) throws Exception {
		String target = request.getArgs()[0];
		if (target.startsWith("#") || target.startsWith("&")) {
			Channel channel = Server.getServer().getChannelManger().getChannel(target);
			for (Client client : channel.getClients())
				request.getConnection().send(Reply.RPL_WHOREPLY, request.getClient(), channel.getName() + " " + client.getUsername() + " * " + client.getHostname() + " " + client.getNickname() + " H+ :" + client.getHopCount() + " " + client.getRealName());
			request.getConnection().send(Reply.RPL_ENDOFWHO, request.getClient(), target + " :End of /WHO list");
		}
		//TODO Servers and wildcards
	}

}
