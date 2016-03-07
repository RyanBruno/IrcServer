package com.rbruno.irc.commands;

import com.rbruno.irc.Channel;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.Error;

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
			if (channel.getUserLimit() > channel.getCurrentNumberOfUsers() || request.getClient().isServerOP() || channel.checkOP(request.getClient())) {
				channel.addClient(request.getConnection().getClient());
				request.getConnection().getClient().addChannels(channel);
			} else {
				request.getConnection().send(Error.ERR_CHANNELISFULL, request.getClient(), channel.getName() + " :Cannot join channel (+l)");
			}

		}

	}

}
