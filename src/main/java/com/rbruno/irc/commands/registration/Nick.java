package com.rbruno.irc.commands.registration;

import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class Nick extends RegCommand {

	public Nick() {
		super("NICK", 1);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (getServer(request).getClientManager().isNick(request.getArgs()[0])) {
			request.getConnection().send(Error.ERR_NICKNAMEINUSE, "*", request.getArgs()[0] + " :Nickname is already in use");
			request.getConnection().close();
			return;
		}
		request.getConnection().setNickname(request.getArgs()[0]);
	}

}
