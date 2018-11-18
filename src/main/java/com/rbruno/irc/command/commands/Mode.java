package com.rbruno.irc.command.commands;

import java.io.IOException;
import java.util.Optional;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Mode extends Command {

	public Mode() {
		super("MODE", 1);
	}

	@Override
	public void execute(Request request, Optional<Client> client) {
		if (request.getArgs().length <= 1) {
			String target = request.getArgs()[0];
			Channel channel = getServer(request).getChannelManger().getChannel(target);
			if (channel == null) {
				request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), target + " :No such channel");
			}
			String modes = "";
			for (char current : channel.getModeMap().keySet())
				modes += current;
			request.getConnection().send(Reply.RPL_CHANNELMODEIS, request.getClient(), target + " +" + modes);
			return;
		}
		String modeFlag = request.getArgs()[1];
		if (!(modeFlag.startsWith("+") || modeFlag.startsWith("-"))) request.getConnection().send(Error.ERR_UMODEUNKNOWNFLAG, request.getClient(), "Unknown MODE flag");
		boolean add = true;
		if (modeFlag.startsWith("-")) add = false;
		if (request.getArgs()[0].matches("(#|&|-|!).*")) {
			channel(request, modeFlag, add);
		} else {
			client(request, modeFlag, add);
		}

	}

	private void channel(ClientRequest request, String modeFlag, boolean add) throws IOException {
		Channel target = getServer(request).getChannelManger().getChannel(request.getArgs()[0]);
		if (target == null) {
			request.getConnection().send(Error.ERR_NOSUCHCHANNEL, request.getClient(), modeFlag + " :No such channel");
			return;
		}
		if (!request.getClient().getModes().contains('o') && !target.checkOP(request.getClient())) {
			request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
			return;
		}
		for (char mode : modeFlag.toLowerCase().substring(1).toCharArray()) {
			switch (mode) {
			case 'o':
				LocalClient clientTarget = getServer(request).getClientManager().getClient(request.getArgs()[2]);
				if (clientTarget == null) {
					request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), request.getArgs()[2] + " :No such nick");
					continue;
				}
				if (add) {
					target.addOP(clientTarget);
				} else {
					target.takeOP(clientTarget);
				}
				target.send(Reply.RPL_CHANNELMODEIS, target.getName() + (add ? " +" : " -") + "o " + clientTarget.getNickname());
				break;
			case 'b':
				break;
			case 'v':
				LocalClient voicee = getServer(request).getClientManager().getClient(request.getArgs()[2]);
				if (voicee != null) {
					if (add) {
						target.giveVoice(voicee);
					} else {
						target.takeVoice(voicee);
					}
				} else {
					request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), ":No such channel");
				}
				target.send(Reply.RPL_CHANNELMODEIS, target.getName() + (add ? " +" : " -") + "v " + voicee.getNickname());
				break;
			default:
				if (request.getArgs().length < 3) {
					target.setMode(mode, add, "");
				} else {
					target.setMode(mode, add, request.getArgs()[2]);
				}
				target.send(Reply.RPL_CHANNELMODEIS, target.getName() + (add ? " +" : " -") + mode);
				break;
			}

		}

	}

	private void client(ClientRequest request, String modeFlag, boolean add) throws IOException {
		if (getServer(request).getClientManager().getClient(request.getArgs()[0]) == null) {
			request.getConnection().send(Error.ERR_NOSUCHNICK, request.getClient(), request.getArgs()[0] + " :No such nick/channel");
			return;
		}
		LocalClient target = getServer(request).getClientManager().getClient(request.getArgs()[0]);
		if (!request.getClient().getModes().contains('o')) {
			request.getConnection().send(Error.ERR_NOPRIVILEGES, request.getClient(), ":Permission Denied- You're not an IRC operator");
			return;
		}
		for (char mode : modeFlag.toLowerCase().substring(1).toCharArray()) {
			if (mode == 'a') {
				target.getConnection().send(Error.ERR_UMODEUNKNOWNFLAG, request.getClient(), ":Use AWAY command!");
				return;
			}
			if (mode == 'o') {
				target.getConnection().send(Error.ERR_UMODEUNKNOWNFLAG, request.getClient(), ":Use OPER command!");
				return;
			}
			target.setMode(mode, add);
			target.getConnection().send(Reply.RPL_UMODEIS, target, request.getClient().getAbsoluteName() + " sets mode " + (add ? "+" : "-") + mode + " on " + target.getNickname());
		}

	}

}
