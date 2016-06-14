package com.rbruno.irc.commands;

import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Request;
import com.rbruno.irc.templates.Channel.ChannelMode;
import com.rbruno.irc.Server;

public class Topic extends Command {

	public Topic() {
		super("TOPIC", 1);
	}

	@Override
	public void execute(Request request) throws Exception {
		Channel channel = Server.getServer().getChannelManger().getChannel(request.getArgs()[0]);
		if (channel == null) {
			request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), request.getArgs()[0] + " :No such channel");
			return;
		}
		if (channel.getMode(ChannelMode.TOPIC) && !channel.checkOP(request.getClient())) {
			request.getConnection().send(Error.ERR_CHANOPRIVSNEEDED, request.getClient(), request.getArgs()[1] + " :You're not channel operator");
			return;
		}
		if (request.getArgs().length == 1) {
			request.getConnection().send(Reply.RPL_TOPIC, request.getClient(), channel.getName() + " " + channel.getTopic());
		} else {
			channel.setTopic(request.getArgs()[1]);
		}
	}

}
