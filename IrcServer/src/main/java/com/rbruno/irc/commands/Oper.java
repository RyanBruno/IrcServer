package com.rbruno.irc.commands;

import com.rbruno.irc.client.Client.ClientMode;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;

public class Oper extends Command {

	public Oper() {
		super("OPER", 2);
	}

	@Override
	public void execute(ClientRequest request) throws Exception {
		if (getServer(request).getConfig().getProperty("DisableOps").equals("true")) {
			request.getConnection().send(Error.ERR_NOOPERHOST, request.getClient(), ":No O-lines for your host");
			return;
		}
		if (getServer(request).getConfig().checkOpPassword(request.getArgs()[0], request.getArgs()[1])) {
			request.getClient().setMode(ClientMode.OPERATOR, true, request.getClient());
			request.getConnection().send(Reply.RPL_YOUREOPER, request.getClient(), ":You are now an IRC operator");
		} else {
			request.getConnection().send(Error.ERR_PASSWDMISMATCH, request.getClient(), ":Password incorrect");
		}
	}
}
