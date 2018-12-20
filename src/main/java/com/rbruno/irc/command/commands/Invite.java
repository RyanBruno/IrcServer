package com.rbruno.irc.command.commands;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Invite extends Command {

	public Invite(CommandContext context) {
		super(context);
	}

	@Override
	public Response[] execute(Request request) {
		Config config = getContext().getConfig();

		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "INVITE", config.getHostname()) };
		}
		
        Channel channel = getContext().getChannel(request.getArgs()[1]);
        
        if (channel == null) {
            return new Response[] {new Response(Error.ERR_NOSUCHCHANNEL, client, request.getArgs()[1] + " :No such channel", config.getHostname())};
        }
        
        if (!channel.isChanOp(client) && !getContext().isop(client)) {
            return new Response[] {new Response(Error.ERR_CHANOPRIVSNEEDED, client, request.getArgs()[1] + " :You're not channel operator", config.getHostname())};
        }
        
        Client target = getContext().getClient(request.getArgs()[0]);
        if (target == null) {
            return new Response[] { new Response(Error.ERR_NOSUCHNICK, client, request.getArgs()[1] + " :No such nick", config.getHostname()) };
        }

        channel.invitePlayer(target);

        Response[] response = new Response[2];

        response[0] = new Response(Reply.RPL_INVITING, client, target.getNickname() + " " + channel.getName(), config.getHostname());
        response[1] = new Response(target, ":" + client.getAbsoluteName() + " INVITE " + target.getNickname() + " " + channel.getName());
        
        return response;
	}

}
