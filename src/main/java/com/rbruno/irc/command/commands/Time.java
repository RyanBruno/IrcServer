package com.rbruno.irc.command.commands;

import java.sql.Timestamp;
import java.util.Date;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandContext;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;
import com.rbruno.irc.reply.Reply;

public class Time extends Command {

    public Time(CommandContext context) {
		super(context);
	}

	@Override
    public Response[] execute(Request request) {
        Date date = new Date();
        Config config = getContext().getConfig();
        
		Client client = getContext().getClient(request.getChannel());
		if (client == null) {
			return new Response[] { new Response(request.getChannel(), "TIME", config.getHostname()) };
		}
        
        return new Response[] { new Response(Reply.RPL_TIME, client, config.getHostname() + " :" + new Timestamp(date.getTime()), config.getHostname())};
    }
}
