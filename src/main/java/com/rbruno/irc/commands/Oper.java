package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Client.ClientMode;
import com.rbruno.irc.templates.Request;

public class Oper extends Command {

	public Oper() {
		super("OPER", 2);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (Server.getServer().getConfig().getProperty("DisableOps").equals("true")) {
			request.getConnection().send(Error.ERR_NOOPERHOST, request.getConnection().getClient(), ":No O-lines for your host");
			return;
		}
		if (Server.getServer().getConfig().checkOpPassword(request.getArgs()[0], request.getArgs()[1])) {
			request.getClient().setMode(ClientMode.OPERATOR, true, request.getClient());
			request.getConnection().send(Reply.RPL_YOUREOPER, request.getConnection().getClient(), ":You are now an IRC operator");
		} else {
			request.getConnection().send(Error.ERR_PASSWDMISMATCH, request.getConnection().getClient(), ":Password incorrect");
		}
	}
}
