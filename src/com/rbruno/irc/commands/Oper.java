package com.rbruno.irc.commands;

import com.rbruno.irc.Error;
import com.rbruno.irc.Reply;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;


public class Oper extends Command {

	public Oper() {
		super("OPER", 2);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (Server.getServer().getConfig().getProperty("DisableOps").equals("true")) {
			request.getConnection().send(Error.ERR_NOOPERHOST, request.getConnection().getClient(), "No O-lines for your host");
			return;
		}
		if (Server.getServer().getConfig().checkOpPassword(request.getArgs()[0], request.getArgs()[1])) {
			// TODO: Tell all clients and servers of the new OP
			request.getClient().setMode(com.rbruno.irc.net.Client.ClientMode.OPERATOR, true, request.getClient());
			request.getConnection().send(Reply.RPL_YOUREOPER, request.getConnection().getClient(), "You are now an IRC operator");
		} else {
			request.getConnection().send(Error.ERR_PASSWDMISMATCH, request.getConnection().getClient(), "Password incorrect");
		}
	}
}
