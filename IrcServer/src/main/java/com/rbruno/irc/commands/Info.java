package com.rbruno.irc.commands;

import java.io.File;

import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Request;
import com.rbruno.irc.util.Utilities;

public class Info extends Command {

	public Info() {
		super("INFO", 0);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (request.getArgs().length == 0) {
			if (new File("/info.txt").exists())
				for (String current : Utilities.read("/info.txt"))
					request.getConnection().send(Reply.RPL_INFO, request.getClient(), ":" + current);
			request.getConnection().send(Reply.RPL_ENDOFINFO, request.getClient(), ":End of /INFO list");
		} else {
			// TODO: Server
		}
	}

}
