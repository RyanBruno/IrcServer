package com.rbruno.irc.command.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.channel.LocalChannel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Join extends Command {

	public Join(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		Config config = getContext().getConfig();

		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "JOIN", config.getHostname()) };
		}

		// TODO Fix Passwords
		String password = null;
		if (request.getArgs().length > 1) {
			password = request.getArgs()[1];
		}

		List<Response> response = new ArrayList<>();

		for (String name : request.getArgs()[0].split(",")) {

			if (!name.startsWith("&") && !name.startsWith("#")) {
				response.add(new Response(Error.ERR_NOSUCHCHANNEL, client, name + " :No such channel", config.getHostname()));
				continue;
			}

			Channel channel = getContext().getChannel(name);

			if (channel == null) {
				channel = new LocalChannel(name, password);
				Server.getServer().getChannelManger().addChannel(channel);
			}

			if (channel.hasClient(client)) {
				continue;
			}

			if (channel.getModes().isInviteOnly() && !channel.isInvited(client)) {
				response.add(new Response(Error.ERR_INVITEONLYCHAN, client, channel.getName() + " :Cannot join channel (+i)", config.getHostname()));
				continue;
			}

			if (channel.isBanned(client)) {
				response.add(new Response(Error.ERR_BANNEDFROMCHAN, client, channel.getName() + " :Cannot join channel (+b)", config.getHostname()));
				continue;
			}

			if (channel.getUsersCount() >= channel.getModes().getuserLimit()) {
				response.add(new Response(Error.ERR_CHANNELISFULL, client, channel.getName() + " :Cannot join channel (+l)", config.getHostname()));
				continue;
			}

			if (channel.getModes().getPassword().isPresent()) {
				if (!channel.getModes().getPassword().get().equals(password)) {
					response.add(new Response(Error.ERR_BADCHANNELKEY, client, channel.getName() + " :Cannot join channel (+k)", config.getHostname()));
					continue;
				}
			}

			channel.addClient(client);

			// JOIN
			Iterator<Client> it = channel.getIterator();
			while(it.hasNext()) {
				Client current = it.next();
				response.add(new Response(current, ":" + client.getAbsoluteName() + " JOIN " + channel.getName()));
			}
			// TOPIC
			response.add(new Response(Reply.RPL_TOPIC, client, channel.getName() + " :" + channel.getTopic(), config.getHostname()));
			// NAMES
			it = channel.getIterator();

	        String message = "@ " + channel.getName() + " :";
	        while (it.hasNext()) {
	            Client current = it.next();
	            if (channel.isChanOp(client)) {
	                message = message + "@" + current.getNickname() + " ";
	            } else if (channel.hasVoice(current)) {
	                message = message + "+" + current.getNickname() + " ";
	            } else {
	                message = message + current.getNickname() + " ";
	            }
	        }
	        response.add(new Response(Reply.RPL_NAMREPLY, client, message, config.getHostname()));
	        response.add(new Response(Reply.RPL_ENDOFNAMES, client, channel.getName() + " :End of /NAMES list.", config.getHostname()));
		}
	}
}
