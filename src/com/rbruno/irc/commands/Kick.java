package com.rbruno.irc.commands;

import com.rbruno.irc.Channel;
import com.rbruno.irc.Error;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.net.Client;

public class Kick extends Command {

	public Kick() {
		super("KICK", 2);
	}
	
	@Override
	public void execute(Request request) throws Exception {
		Channel channel = Server.getServer().getChannelManger().getChannel(request.getArgs()[0]);
		if(channel == null) {
			request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), request.getArgs()[1] + " :No such channel");
			return;
		}
		if (!channel.checkOP(request.getClient())) {
			request.getConnection().send(Error.ERR_CHANOPRIVSNEEDED, request.getClient(), request.getArgs()[1] + " :You're not channel operator");
			return;
		}
		Client target = Server.getServer().getClientManager().getClient(request.getArgs()[1]);
		if(target == null) {
			request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), request.getArgs()[1] + " :No such nick");
			return;
		}
		if (!channel.isUserOnChannel(target)) {
			request.getConnection().send(Error.ERR_USERNOTINCHANNEL, request.getClient(), target.getNickname() + " " + channel.getName() + " :User is not on that channel");
			return;
		}
		channel.removeClient(target);
		target.removeChannel(channel);
		
	}

}
