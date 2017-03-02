package com.rbruno.irc.commands.client;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.reply.Error;

public class Whois extends ClientCommand {

	public Whois() {
		super("WHOIS", 1);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		for (String current : request.getArgs()[0].split(",")) {
			Client target = getServer(request).getClientManager().getClient(current);
			if (target == null) {
				request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), current + " :No such nick");
				continue;
			}

			request.getConnection().send(Reply.RPL_WHOISUSER, request.getClient(), target.getNickname() + " " + target.getUsername() + " " + getServer(request).getConfig().getProperty("hostname") + " * :" + target.getRealName());
			// TODO Whois server
			if (target.getModes().contains('o')) request.getConnection().send(Reply.RPL_WHOISOPERATOR, request.getClient(), target.getNickname() + " :is an IRC operator");
			if (!target.getAwayMessage().equals("")) request.getConnection().send(Reply.RPL_AWAY, request.getClient(), target.getNickname() + " :" + target.getAwayMessage());
			String channels = "";
			for (Channel channel : target.getChannels()) {
				if (channel.checkOP(target)) {
					channels = channels + "@" + channel.getName() + " ";
				} else if (channel.hasVoice(target)) {
					channels = channels + "+" + channel.getName() + " ";
				} else {
					channels = channels + channel.getName() + " ";
				}
			}
			request.getConnection().send(Reply.RPL_WHOISCHANNELS, request.getClient(), target.getNickname() + " :" + channels);
			request.getConnection().send(Reply.RPL_ENDOFWHOIS, request.getClient(), target.getNickname() + " :End of /WHOIS list");

		}

	}

}
