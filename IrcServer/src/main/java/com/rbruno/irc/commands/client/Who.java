package com.rbruno.irc.commands.client;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Who extends ClientCommand {

	public Who() {
		super("WHO", 1);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		String target = request.getArgs()[0];
		Channel channel = getServer(request).getChannelManger().getChannel(target);
		if (channel == null) {
			request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), " :No such channel");
			return;
		}
		for (Client client : channel.getClients())
			request.getConnection().send(Reply.RPL_WHOREPLY, request.getClient(), channel.getName() + " " + client.getUsername() + " * " + client.getHostname() + " " + client.getNickname() + " H+ :" + client.getHopCount() + " " + client.getRealName());
		request.getConnection().send(Reply.RPL_ENDOFWHO, request.getClient(), target + " :End of /WHO list");

	}

}
