package com.rbruno.irc.commands;

import java.util.ArrayList;

import com.rbruno.irc.reply.Error;
import com.rbruno.irc.templates.Request;

public class Command {

	private String command;
	private int parameters;

	private static ArrayList<Command> commands = new ArrayList<Command>();

	public static void init() {
		commands.add(new Pass());
		commands.add(new Nick());
		commands.add(new User());
		// commands.add(new Server());
		commands.add(new Oper());
		commands.add(new Quit());
		// commands.add(new Squit());
		commands.add(new Join());
		commands.add(new Part());
		commands.add(new Mode());
		commands.add(new Topic());
		commands.add(new Names());
		commands.add(new List());
		commands.add(new Invite());
		commands.add(new Kick());
		commands.add(new Version());
		// commands.add(new Stats());
		// commands.add(new Links());
		commands.add(new Time());
		// commands.add(new Connect());
		// commands.add(new Trace());
		commands.add(new Admin());
		commands.add(new Info());
		commands.add(new Privmsg());

		commands.add(new Who());
		commands.add(new Ping());
	}

	/**
	 * Creates a new Command object.
	 * 
	 * @param command
	 *            Name of the Command. Not cap sensitive.
	 * @param parameters
	 *            Required number of parameters.
	 */
	public Command(String command, int parameters) {
		this.command = command;
		this.parameters = parameters;
	}

	/**
	 * Override this.
	 * 
	 * @param request
	 *            Request that was sent.
	 * @throws Exception
	 */
	public void execute(Request request) throws Exception {
	}

	/**
	 * Returns the command.
	 * 
	 * @return The command.
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Returns the required number of parameters.
	 * 
	 * @return The required number of parameters.
	 */
	public int getParameters() {
		return parameters;
	}

	/**
	 * Searches for the command that the request was asking for and runs it
	 * passing request.
	 * 
	 * @param request
	 * @throws Exception
	 */
	public static void runCommand(Request request) throws Exception {
		if (request.getClient() != null) request.getClient().setLastCheckin(System.currentTimeMillis());
		Command command = getCommand(request.getCommand());
		if (command == null) {
			if (request.getClient() != null) request.getConnection().send(Error.ERR_UNKNOWNCOMMAND, request.getClient(), request.getCommand() + " :Unknown command");
			return;
		}
		if (request.getArgs().length < command.getParameters()) {
			request.getConnection().send(Error.ERR_NEEDMOREPARAMS, request.getConnection().getClient(), ":Not enough parameters");
			return;
		}
		command.execute(request);
	}

	private static Command getCommand(String command) {
		for (Command current : commands)
			if (current.getCommand().equalsIgnoreCase(command)) return current;
		return null;
	}
}
