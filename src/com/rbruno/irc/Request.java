package com.rbruno.irc;

import com.rbruno.irc.net.Connection;

public class Request {

	private Connection connection;
	private String prefix;
	private String command;
	private String[] args;

	public Request(Connection connection, String request) {
		this.connection = connection;
		if (request.startsWith(":")) {
			this.prefix = request.split(" ")[0];
			request = request.substring(prefix.length() + 1);
		}
		this.command = request.split(" ")[0];
		request = request.substring(command.length() + 1);
		String postDelimiter = request.substring(request.split(":")[0].length());
		request = request.substring(0, postDelimiter.length());
		if (request.length() > 0)  this.args = request.split(" ");
		
		if (postDelimiter.length() > 0) {
			String[] newArgs = new String[args.length + 1];
			for (int i = 0; i < args.length; i++) {
				newArgs[i] = args[i];
			}
			newArgs[newArgs.length - 1] = postDelimiter;
			this.args = newArgs;
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getCommand() {
		return command;
	}

	public String[] getArgs() {
		return args;
	}
}