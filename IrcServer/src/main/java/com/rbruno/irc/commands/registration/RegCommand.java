package com.rbruno.irc.commands.registration;

import java.util.ArrayList;

import com.rbruno.irc.commands.Command;
import com.rbruno.irc.net.Request;

public class RegCommand extends Command {

	private static ArrayList<RegCommand> commands = new ArrayList<RegCommand>();

	public RegCommand(String command, int parameters) {
		super(command, parameters);
	}

	public void execute(Request request) throws Exception {
	}

	public static void init() {
		commands.add(new Pass());
		commands.add(new Nick());
		commands.add(new User());
		// commands.add(new Server());
	}

	public static void runCommand(Request request) throws Exception {
		request.getConnection().getServer().getPluginManager().runOnRequest(request);
		if (request.isCancelled()) return;
		RegCommand command = getCommand(request.getCommand());
		if (command == null || request.getArgs().length < command.getParameters()) return;
		command.execute(request);
	}

	private static RegCommand getCommand(String command) {
		for (RegCommand current : commands) 
			if (current.getCommand().equalsIgnoreCase(command)) return current;
		return null;
	}

}
