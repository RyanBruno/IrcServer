package com.rbruno.irc.commands;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.Channel.ChannelMode;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Join extends Command {

	public Join() {
		super("JOIN", 1);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		// TODO: Check password
		String[] channels = request.getArgs()[0].split(",");
		for (String channelName : channels) {
			Channel channel = getServer(request).getChannelManger().getChannel(channelName);
			if (channel == null) {
				if (!getServer(request).getConfig().getProperty("CreateOnJoin").equals("true")) {
					request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), channelName + " :No such channel");
					continue;
				}
				if (!channelName.startsWith("#") && !channelName.startsWith("&")) continue;
				String password = "";
				if (request.getArgs().length >= 2) password = request.getArgs()[1];
				channel = new Channel(request.getArgs()[0], password, false, getServer(request));
				channel.addOP(request.getClient());
				getServer(request).getChannelManger().addChannel(channel);
			}
			if (channel.getUserLimit() == -1 || channel.getUserLimit() > channel.getUsersCount() || request.getClient().isServerOP()) {
				if (channel.checkPassword((request.getArgs().length >= 2) ? request.getArgs()[1] : "")) {
					if (channel.getMode(ChannelMode.INVITE_ONLY) && !channel.isUserInvited(request.getClient()) && !request.getClient().isServerOP()) {
						request.getConnection().send(Error.ERR_INVITEONLYCHAN, request.getClient(), channel.getName() + " :Cannot join channel (+i)");
						continue;
					}
					channel.addClient(request.getClient());
					request.getClient().addChannel(channel);
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
