package com.rbruno.irc.command.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Reply;

public class Version extends ClientCommand {

	public Version() {
		super("VERSION", 0);
	}

	@Override
	public void execute(ClientRequest request) {
		if (request.getArgs().length == 0) {
			request.getConnection().send(Reply.RPL_VERSION, request.getClient(), Server.getVersion() + " " + getServer(request).getConfig().getProperty("hostname"));
		} else {
			//TODO: Server
		}
	}

}
