package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Request;

public class Join extends Command {

	public Join() {
		super("JOIN", 1);
	}

	@Override
	public void execute(Request request) throws Exception {
		// TODO: Check password
		String[] channels = request.getArgs()[0].split(",");
		for (String channelName : channels) {
			Channel channel = Server.getServer().getChannelManger().getChannel(channelName);
			if (channel == null) {
				request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), channelName + " :No such channel");
				continue;
			}
			if (channel.getUserLimit() == -1 || channel.getUserLimit() > channel.getCurrentNumberOfUsers() || request.getClient().isServerOP()) {
				if (channel.checkPassword((request.getArgs().length >= 2) ? request.getArgs()[1] : "")) {
					channel.addClient(request.getConnection().getClient());
					request.getConnection().getClient().addChannel(channel);
					request.getConnection().send(Reply.RPL_TOPIC, request.getClient(), channel.getName() + " :" + channel.getTopic());
				} else {
					request.getConnection().send(Error.ERR_BADCHANNELKEY, request.getClient(), channel.getName() + " :Cannot join channel (+k)");
				}

			} else {
				request.getConnection().send(Error.ERR_CHANNELISFULL, request.getClient(), channel.getName() + " :Cannot join channel (+l)");
			}

		}

	}
}
