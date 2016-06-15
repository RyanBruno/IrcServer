package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Channel.ChannelMode;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Privmsg extends Command {

	public Privmsg() {
		super("PRIVMSG", 2);
	}

	@Override
	public void execute(Request request) throws Exception {
		for (String reciver : request.getArgs()[0].split(",")) {
			if (reciver.startsWith("$")) {
				// TODO: Server
			} else if (reciver.startsWith("#") || reciver.startsWith("&")) {
				Channel channel = Server.getServer().getChannelManger().getChannel(reciver);
				if (!request.getClient().getChannels().contains(channel) && channel.getMode(ChannelMode.NO_MESSAGE_BY_OUTSIDE)) {
					request.getConnection().send(Error.ERR_CANNOTSENDTOCHAN, request.getClient(), channel.getName() + " :Cannot send to channel");
					return;
				}
				channel.sendMessage(request.getClient(), request.getArgs()[1]);
			} else {
				Client client = Server.getServer().getClientManager().getClient(reciver);
				if (client != null) {
					client.getConnection().send(":" + request.getClient().getAbsoluteName() + " PRIVMSG " + client.getNickname() + " " + request.getArgs()[1]);
				} else {
					request.getClient().getConnection().send(Error.ERR_NOSUCHNICK, client, reciver + " :No such nick");
				}
			}
		}

	}

}
