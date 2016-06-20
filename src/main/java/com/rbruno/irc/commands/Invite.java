package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Invite extends Command {

	public Invite() {
		super("INVITE", 2);
	}

	@Override
	public void execute(Request request) throws Exception {
		Channel channel = Server.getServer().getChannelManger().getChannel(request.getArgs()[1]);
		if (channel == null) {
			request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), request.getArgs()[1] + " :No such channel");
			return;
		}
		if (!channel.checkOP(request.getClient()) && !request.getClient().isServerOP()) {
			request.getConnection().send(Error.ERR_CHANOPRIVSNEEDED, request.getClient(), request.getArgs()[1] + " :You're not channel operator");
			return;
		}
		Client target = Server.getServer().getClientManager().getClient(request.getArgs()[0]);
		if (target == null) {
			request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), request.getArgs()[1] + " :No such nick");
			return;
		}
		channel.inviteUser(target);
		request.getConnection().send(Reply.RPL_INVITING, request.getClient(), target.getNickname() + " " + channel.getName());
		target.getConnection().send(":" + request.getClient().getAbsoluteName() + " INVITE " + target.getNickname() + " " + channel.getName());
	}

}
