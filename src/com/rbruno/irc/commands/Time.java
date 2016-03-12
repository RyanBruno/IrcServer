package com.rbruno.irc.commands;

import java.sql.Timestamp;

import com.rbruno.irc.Reply;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;

public class Time extends Command {

	public Time() {
		super("TIME", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0) {
			java.util.Date date = new java.util.Date();
			request.getConnection().send(Reply.RPL_TIME, request.getClient(), Server.getServer().getConfig().getProperty("ServerName") + " :" + new Timestamp(date.getTime()));
		} else {
			//TODO: Servers
		}
	}
}
