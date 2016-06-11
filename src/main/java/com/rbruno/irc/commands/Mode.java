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
		String argument = request.getArgs()[1];

		if (!(argument.startsWith("+") || argument.startsWith("-"))) request.getConnection().send(Error.ERR_UMODEUNKNOWNFLAG, request.getClient(), "Unknown MODE flag");
		boolean add = true;
		if (argument.startsWith("-")) add = false;

		if (request.getArgs()[0].startsWith("#") || request.getArgs()[0].startsWith("&")) {
			// Channels
			if (Server.getServer().getChannelManger().getChannel(request.getArgs()[0]) != null) {
				Channel target = Server.getServer().getChannelManger().getChannel(request.getArgs()[0]);
				for (char mode : argument.toLowerCase().toCharArray()) {
					switch (mode) {
					case 'o':
						//TODO op
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.addOP(Server.getServer().getClientManager().getClient(request.getArgs()[1]));
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'p':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.setMode(ChannelMode.PRIVATE, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 's':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.setMode(ChannelMode.SECRET, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'i':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.setMode(ChannelMode.INVITE_ONLY, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 't':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.setMode(ChannelMode.TOPIC, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'n':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.setMode(ChannelMode.NO_MESSAGE_BY_OUTSIDE, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'm':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.setMode(ChannelMode.MODERATED_CHANNEL, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'l':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							try {
								int limit = Integer.parseInt(request.getArgs()[2]);
								target.setUserLimit(limit);
							} catch (NumberFormatException e) {
								request.getConnection().send(Error.ERR_NEEDMOREPARAMS, request.getClient(), ":Not enough parameters");
							}

						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'b':
						break;
					case 'v':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							Client voicee = Server.getServer().getClientManager().getClient(request.getArgs()[2]);
							if (voicee != null) {
								target.giveVoice(voicee);
							} else {
								request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), ":No such channel");
							}
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'k':
						if (request.getClient().isServerOP() || target.checkOP(request.getClient())) {
							target.setPassword(request.getArgs()[2]);

						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					}
				}
			} else {
				request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), argument + " :No such channel");
			}
		} else {
			// Not channel
			if (Server.getServer().getClientManager().getClient(request.getArgs()[0]) != null) {
				Client target = Server.getServer().getClientManager().getClient(request.getArgs()[0]);

				for (char mode : argument.toLowerCase().toCharArray()) {
					switch (mode) {
					case 'i':
						if (target == request.getClient() || request.getClient().getMode(Client.ClientMode.OPERATOR)) {
							target.setMode(Client.ClientMode.INVISIBLE, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 's':
						if (target == request.getClient() || request.getClient().getMode(Client.ClientMode.OPERATOR)) {
							target.setMode(Client.ClientMode.SERVER_NOTICES, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					case 'w':
						if (request.getClient().getMode(Client.ClientMode.OPERATOR)) {
							target.setMode(Client.ClientMode.WALLOPS, add, request.getClient());
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}

						break;
					case 'o':
						if (request.getClient().hasMode(Client.ClientMode.OPERATOR)) {
							target.setMode(Client.ClientMode.OPERATOR, add, request.getClient());
							target.getConnection().send(Reply.RPL_YOUREOPER, request.getConnection().getClient(), ":You are now an IRC operator");
						} else {
							request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
						}
						break;
					}
				}
			} else {
				request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), argument + " :No such nick/channel");
			}
		}

	}

}
