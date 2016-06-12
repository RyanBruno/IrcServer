package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Channel;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;
import com.rbruno.irc.templates.Channel.ChannelMode;

public class Mode extends Command {

	public Mode() {
		super("MODE", 1);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length <= 1) {
			String target = request.getArgs()[0];
			if (request.getArgs()[0].startsWith("#") || request.getArgs()[0].startsWith("&")) {
				// Channel
				//TODO list channel modes
			} else {
				// Not Channel
				//TODO list user modes
			}
		} else {
			String modeFlag = request.getArgs()[1];
			if (!(modeFlag.startsWith("+") || modeFlag.startsWith("-"))) request.getConnection().send(Error.ERR_UMODEUNKNOWNFLAG, request.getClient(), "Unknown MODE flag");
			boolean add = true;
			if (modeFlag.startsWith("-")) add = false;
			if (request.getArgs()[0].startsWith("#") || request.getArgs()[0].startsWith("&")) {
				// Channels
				if (Server.getServer().getChannelManger().getChannel(request.getArgs()[0]) != null) {
					Channel target = Server.getServer().getChannelManger().getChannel(request.getArgs()[0]);
					for (char mode : modeFlag.toLowerCase().toCharArray()) {
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							switch (mode) {
							case 'o':
								// TODO op
								target.addOP(Server.getServer().getClientManager().getClient(request.getArgs()[1]));
								break;
							case 'p':
								target.setMode(ChannelMode.PRIVATE, add, request.getClient());
								break;
							case 's':
								target.setMode(ChannelMode.SECRET, add, request.getClient());
								break;
							case 'i':
								target.setMode(ChannelMode.INVITE_ONLY, add, request.getClient());
								break;
							case 't':
								target.setMode(ChannelMode.TOPIC, add, request.getClient());
								break;
							case 'n':
								target.setMode(ChannelMode.NO_MESSAGE_BY_OUTSIDE, add, request.getClient());
								break;
							case 'm':
								target.setMode(ChannelMode.MODERATED_CHANNEL, add, request.getClient());
								break;
							case 'l':
								try {
									int limit = Integer.parseInt(request.getArgs()[2]);
									target.setUserLimit(limit);
								} catch (NumberFormatException e) {
									request.getConnection().send(Error.ERR_NEEDMOREPARAMS, request.getClient(), ":Not enough parameters");
								}
								break;
							case 'b':
								break;
							case 'v':
								Client voicee = Server.getServer().getClientManager().getClient(request.getArgs()[2]);
								if (voicee != null) {
									target.giveVoice(voicee);
								} else {
									request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), ":No such channel");
								}
								break;
							case 'k':
								target.setPassword(request.getArgs()[2]);
								break;
							}
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
					}
				} else {
					request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), modeFlag + " :No such channel");
				}
			} else {
				// Not channel
				if (Server.getServer().getClientManager().getClient(request.getArgs()[0]) != null) {
					Client target = Server.getServer().getClientManager().getClient(request.getArgs()[0]);
					if (request.getClient().isServerOP()) {
						for (char mode : modeFlag.toLowerCase().toCharArray()) {
							switch (mode) {
							case 'i':
								if (target == request.getClient()) {
									target.setMode(Client.ClientMode.INVISIBLE, add, request.getClient());
								} else {
									request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
								}
								break;
							case 's':
								if (target == request.getClient()) {
									target.setMode(Client.ClientMode.SERVER_NOTICES, add, request.getClient());
								} else {
									request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
								}
								break;
							case 'w':
								target.setMode(Client.ClientMode.WALLOPS, add, request.getClient());
								break;
							case 'o':
								target.setMode(Client.ClientMode.OPERATOR, add, request.getClient());
								target.getConnection().send(Reply.RPL_YOUREOPER, request.getConnection().getClient(), ":You are now an IRC operator");
								break;
							}
						}
					} else {
						request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
					}
				} else {
					request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), modeFlag + " :No such nick/channel");
				}
			}
		}
	}

}
