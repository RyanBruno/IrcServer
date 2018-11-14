package com.rbruno.irc.command.commands;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Error;

public class Kick extends ClientCommand {

	public Kick() {
		super("KICK", 2);
	}

	@Override
	public void execute(ClientRequest request) {
		Channel channel = getServer(request).getChannelManger().getChannel(request.getArgs()[0]);
		if (channel == null) {
			request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), request.getArgs()[1] + " :No such channel");
			return;
		}
		if (!channel.checkOP(request.getClient()) && !request.getClient().getModes().contains('o')) {
			request.getConnection().send(Error.ERR_CHANOPRIVSNEEDED, request.getClient(), request.getArgs()[1] + " :You're not channel operator");
			return;
		}
		Client target = getServer(request).getClientManager().getClient(request.getArgs()[1]);
		if (target == null) {
			request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), request.getArgs()[1] + " :No such nick");
			return;
		}
		if (!channel.isUserOnChannel(target)) {
			request.getConnection().send(Error.ERR_USERNOTINCHANNEL, request.getClient(), target.getNickname() + " " + channel.getName() + " :User is not on that channel");
			return;
		}
		// TODO Send kick msg to target
		String message = ":You have been kicked from the channel";
		if (request.getArgs().length >= 3) message = request.getArgs()[2];
		for (Client client : channel.getClients())
			client.getConnection().send(":" + request.getClient().getAbsoluteName() + " KICK " + channel.getName() + " " + target.getNickname() + " " + message);

		channel.removeClient(target);
		target.removeChannel(channel);

	}

}
