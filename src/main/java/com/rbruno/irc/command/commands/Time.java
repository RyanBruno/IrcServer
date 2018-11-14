package com.rbruno.irc.command.commands;

import java.sql.Timestamp;
import java.util.Date;

import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Reply;

public class Time extends ClientCommand {

	public Time() {
		super("TIME", 0);
	}

	@Override
	public void execute(ClientRequest request) {
		if (request.getArgs().length == 0) {
			Date date = new Date();
			request.getConnection().send(Reply.RPL_TIME, request.getClient(), getServer(request).getConfig().getProperty("hostname") + " :" + new Timestamp(date.getTime()));
		} else {
			//TODO: Servers
		}
	}
}
