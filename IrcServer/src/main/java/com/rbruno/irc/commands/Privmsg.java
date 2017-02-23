package com.rbruno.irc.commands;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Error;

public class Privmsg extends Command {

	public Privmsg() {
		super("PRIVMSG", 2);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		for (String reciver : request.getArgs()[0].split(",")) {
			if (reciver.startsWith("$")) {
				// TODO: Server
			} else if (reciver.startsWith("#") || reciver.startsWith("&")) {
				Channel channel = getServer(request).getChannelManger().getChannel(reciver);
				if (channel == null) {
					request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), reciver + " :No such channel");
					return;
				}
				if (!request.getClient().getChannels().contains(channel) && channel.isMode('n')) {
					request.getConnection().send(Error.ERR_CANNOTSENDTOCHAN, request.getClient(), channel.getName() + " :Cannot send to channel");
					return;
				}
				if (!channel.hasVoice(request.getClient()) && channel.isMode('m')) {
					request.getConnection().send(Error.ERR_CANNOTSENDTOCHAN, request.getClient(), channel.getName() + " :Cannot send to channel");
					return;
				}
				channel.sendMessage(request.getClient(), request.getArgs()[1]);

			} else {
				Client client = getServer(request).getClientManager().getClient(reciver);
				if (client != null) {
					client.getConnection().send(":" + request.getClient().getAbsoluteName() + " PRIVMSG " + client.getNickname() + " " + request.getArgs()[1]);
				} else {
					request.getClient().getConnection().send(Error.ERR_NOSUCHNICK, client, reciver + " :No such nick");
				}
			}
		}
	}

}
