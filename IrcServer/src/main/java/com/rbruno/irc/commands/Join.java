package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Channel.ChannelMode;
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
				if (!Server.getServer().getConfig().getProperty("CreateOnJoin").equals("true")) {
					request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), channelName + " :No such channel");
					continue;
				}
				if (!channelName.startsWith("#") && !channelName.startsWith("&")) continue;
				String password = "";
				if (request.getArgs().length >= 2) password = request.getArgs()[1];
				channel = new Channel(request.getArgs()[0], password, false);
				channel.addOP(request.getClient());
				Server.getServer().getChannelManger().addChannel(channel);
			}
			if (channel.getUserLimit() == -1 || channel.getUserLimit() > channel.getUsersCount() || request.getClient().isServerOP()) {
				if (channel.checkPassword((request.getArgs().length >= 2) ? request.getArgs()[1] : "")) {
					if (channel.getMode(ChannelMode.INVITE_ONLY) && !channel.isUserInvited(request.getClient()) && !request.getClient().isServerOP()) {
						request.getConnection().send(Error.ERR_INVITEONLYCHAN, request.getClient(), channel.getName() + " :Cannot join channel (+i)");
						continue;
					}
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
