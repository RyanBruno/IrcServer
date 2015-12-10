package com.rbruno.irc.commands;

import java.util.ArrayList;

import com.rbruno.irc.Error;
import com.rbruno.irc.Request;

public class Command {

	private String command;
	private int parameters;

	private static ArrayList<Command> commands = new ArrayList<Command>();

	public static void init() {
		commands.add(new Pass());
		commands.add(new Nick());
	}

	public Command(String command, int parameters) {
		this.command = command;
		this.parameters = parameters;
	}

	public void execute(Request request) throws Exception {
	}

	public String getCommand() {
		return command;
	}

	public int getParameters() {
		return parameters;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public void setParameters(int parameters) {
		this.parameters = parameters;
	}

	public static boolean isCommand(String command) {
		for (Command curret : commands) {
			if (curret.getCommand().equalsIgnoreCase(command)) return true;
		}
		return false;
	}

	public static Command getCommand(String command) {
		for (Command current : commands) {
			if (current.getCommand().equalsIgnoreCase(command)) return current;
		}
		return null;
	}

	public static void runCommand(String commandName, Request request) throws Exception {
		Command command = getCommand(commandName);
		if (request.getArgs().length < command.getParameters()) {
			request.getConnection().send(Error.ERR_NEEDMOREPARAMS, request.getConnection().getClient(), "Not enough parameters");
			return;
		}
		command.execute(request);

	}
}
