package com.rbruno.irc;

import java.lang.String;

public class Request {

	private String prefix;
	private String command;
	private String[] args;

	public Request(String request) {
		if (request.startsWith(":")){
			this.prefix = request.split(" ")[0];
			request = request.substring(prefix.length() + 1);
		}
		this.command = request.split(" ")[0];
		request = request.substring(command.length() + 1);
		this.args = request.split(" ");
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