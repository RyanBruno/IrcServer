package com.rbruno.irc.commands.client;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.LocalChannel;
import com.rbruno.irc.channel.ModlessChannel;
import com.rbruno.irc.channel.NormalChannel;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Join extends ClientCommand {

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
				String password = "";
				if (request.getArgs().length >= 2)
					password = request.getArgs()[1];
				switch (channelName.charAt(0)) {
				case '#':
					channel = new NormalChannel(channelName, password, getServer(request));
					break;
				case '&':
					channel = new LocalChannel(channelName, password, getServer(request));
					break;
				case '+':
					channel = new ModlessChannel(channelName, password, getServer(request));
					break;
				default:
					request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), channelName + " :No such channel");
					continue;
				}
				channel = new Channel(channelName, password, getServer(request));
				channel.addOP(request.getClient());
				getServer(request).getChannelManger().addChannel(channel);
			}
			if (channel.isUserOnChannel(request.getClient()))
				return;

			try {
				Integer.parseInt(channel.getMode('l'));
				if (Integer.parseInt(channel.getMode('l')) < channel.getUsersCount() && !request.getClient().getModes().contains('o')) {
					request.getConnection().send(Error.ERR_CHANNELISFULL, request.getClient(), channel.getName() + " :Cannot join channel (+l)");
					continue;
				}
			} catch (NumberFormatException e) {

			}

			if (!channel.checkPassword((request.getArgs().length >= 2) ? request.getArgs()[1] : "")) {
				request.getConnection().send(Error.ERR_BADCHANNELKEY, request.getClient(), channel.getName() + " :Cannot join channel (+k)");
				continue;
			}

			if (channel.isMode('i') && !request.getClient().isInvitedTo(channel) && !request.getClient().getModes().contains('o')) {
				request.getConnection().send(Error.ERR_INVITEONLYCHAN, request.getClient(), channel.getName() + " :Cannot join channel (+i)");
				continue;
			}
			channel.addClient(request.getClient());
			request.getClient().addChannel(channel);
			request.getConnection().send(Reply.RPL_TOPIC, request.getClient(), channel.getName() + " :" + channel.getTopic());

		}

	}
}
