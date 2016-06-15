package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Kick extends Command {

	public Kick() {
		super("KICK", 2);
	}

	@Override
	public void execute(Request request) throws Exception {
		Channel channel = Server.getServer().getChannelManger().getChannel(request.getArgs()[0]);
		if (channel == null) {
			request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), request.getArgs()[1] + " :No such channel");
			return;
		}
		if (!channel.checkOP(request.getClient()) && !request.getClient().isServerOP()) {
			request.getConnection().send(Error.ERR_CHANOPRIVSNEEDED, request.getClient(), request.getArgs()[1] + " :You're not channel operator");
			return;
		}
		Client target = Server.getServer().getClientManager().getClient(request.getArgs()[1]);
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
