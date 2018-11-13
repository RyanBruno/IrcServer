package com.rbruno.irc.commands;

import com.rbruno.irc.Server;
import com.rbruno.irc.net.Request;

public class Command {

	private String command;
	private int parameters;

	public Command(String command, int parameters) {
		this.command = command;
		this.parameters = parameters;
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

}
