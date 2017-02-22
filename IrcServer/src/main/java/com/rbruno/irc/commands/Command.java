package com.rbruno.irc.commands;

import java.util.ArrayList;

import com.rbruno.irc.Server;
import com.rbruno.irc.net.ClientRequest;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

/**
 * A command that can be called when requested by a client. Also statically hold
 * all commands in an array.
 */
public class Command {

	private String command;
	private int parameters;

	private static ArrayList<Command> commands = new ArrayList<Command>();

	public static void init() {
//TODO: Nick
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
		commands.add(new Notice());
		commands.add(new Who());
		commands.add(new Whois());
		// commands.add(new Whowas());
		// commands.add(new Kill());
		commands.add(new Ping());
		commands.add(new Pong());
		// commands.add(new Error());
		// Optional Commands
		commands.add(new Away());
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
	public void execute(ClientRequest request) throws Exception {
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
	
	public Server getServer(Request request) {
		return request.getConnection().getServer();
	}

	/**
	 * Searches for the command that the request was asking for and runs it
	 * passing request.
	 * 
	 * @param request
	 *            The request sent by the client.
	 * @throws Exception
	 */
	public static void runCommand(ClientRequest request) throws Exception {
		if (request.getClient() != null) request.getClient().setLastCheckin(System.currentTimeMillis());
		request.getConnection().getServer().getPluginManager().runOnRequest(request);
		if (request.isCancelled()) return;
		Command command = getCommand(request.getCommand());
		if (command == null) {
			if (request.getClient() != null) request.getConnection().send(Error.ERR_UNKNOWNCOMMAND, request.getClient(), request.getCommand() + " :Unknown command");
			return;
		}
		if (request.getArgs().length < command.getParameters()) {
			request.getConnection().send(Error.ERR_NEEDMOREPARAMS, request.getClient(), ":Not enough parameters");
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
