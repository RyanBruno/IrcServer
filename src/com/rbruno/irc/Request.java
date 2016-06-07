package com.rbruno.irc;

import com.rbruno.irc.net.Client;
import com.rbruno.irc.net.Connection;

public class Request {

	private Connection connection;
	private String prefix;
	private String command;
	private String[] args = new String[0];
	private Client client;

	public Request(Connection connection, String request) throws Exception {
		this.connection = connection;
		if (request.startsWith(":")) {
			this.prefix = request.split(" ")[0].substring(1);
			request = request.substring(prefix.length() + 1);
		}
		this.command = request.split(" ")[0];

		if (request.length() != command.length()) {
			request = request.substring(command.length() + 1);
		} else {
			request = request.substring(command.length());
		}
		String postDelimiter = request.substring(request.split(":")[0].length());
		request = request.substring(0, request.length() - postDelimiter.length());
		if (request.length() > 0)
			this.args = request.split(" ");
		if (postDelimiter.length() > 0) {
			String[] newArgs = new String[args.length + 1];
			for (int i = 0; i < args.length; i++) {
				newArgs[i] = args[i];
			}
			newArgs[newArgs.length - 1] = postDelimiter;
			this.args = newArgs;
		}
		if (prefix != null && connection.isServer()) {
			client = Server.getServer().getClientManager().getClient(prefix);
		} else if (connection.isClient()) {
			client = connection.getClient();
		} else {
			client = null;
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

	public Client getClient() {
		return client;
	}

}