package com.rbruno.irc.commands;

import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.net.Client;

public class Mode extends Command {

	public Mode() {
		super("MODE", 1);
	}

	@Override
	public void execute(Request request) throws Exception {
		if (Server.getServer().getClientManager().getClient(request.getArgs()[0]) != null) {
			// Client mode
			String argument = request.getArgs()[1];
			
			if (!(argument.startsWith("+") || argument.startsWith("-")));//TODO: Throw error
			
			boolean add = true;
			if (argument.startsWith("+")) add = true;
			if (argument.startsWith("-")) add = false;

			for (char mode : argument.toLowerCase().toCharArray()) {
				switch (mode) {
				case 'i':
					request.getConnection().getClient().setMode(Client.Mode.INVISIBLE, add);
					break;
				case 's':
					request.getConnection().getClient().setMode(Client.Mode.SERVER_NOTICES, add);
					break;
				case 'w':
					request.getConnection().getClient().setMode(Client.Mode.WALLOPS, add);
					break;
				case 'o':
					request.getConnection().getClient().setMode(Client.Mode.OPERATOR, add);
					break;
				}
			}
		}
		if (Server.getServer().getChannelManger().getChannel(request.getArgs()[0]) != null) {
			// Channel mode
		}
	}

}
