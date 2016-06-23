package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

public class Whois extends Command {

	public Whois() {
		super("WHOIS", 2);
	}

	@Override
	public void execute(Request request) throws Exception {
		for (String current : request.getArgs()[1].split(",")) {
			Client target = Server.getServer().getClientManager().getClient(current);
			request.getConnection().send(Reply.RPL_WHOISUSER, request.getClient(), target.getNickname() + " " + target.getUsername() + " " + Server.getServer().getConfig().getProperty("hostname") + " * :" + target.getRealName());
			// TODO Whois server
			if (target.isServerOP()) request.getConnection().send(Reply.RPL_WHOISOPERATOR, request.getClient(), target.getNickname() + " :is an IRC operator");
			if (!target.getAwayMessage().equals(""))request.getConnection().send(Reply.RPL_AWAY, request.getClient(), target.getNickname() + " :" + target.getAwayMessage());
			//request.getConnection().send(Reply.RPL_WHOISIDLE, request.getClient(), target.getNickname() + " 0 :seconds idle");
			request.getConnection().send(Reply.RPL_ENDOFWHOIS, request.getClient(), target.getNickname() + " :End of /WHOIS list");
			String channels = "";
			for (Channel channel : target.getChannels()) {
				if (channel.checkOP(target) || target.isServerOP()) {
					channels = channels + "@" + channel.getName() + " ";
				} else if (channel.hasVoice(target)) {
					channels = channels + "+" + channel.getName() + " ";
				} else {
					channels = channels + channel.getName() + " ";
				}
			}
			request.getConnection().send(Reply.RPL_WHOISCHANNELS, request.getClient(), target.getNickname() + " :" + channels);
		}

	}

}
