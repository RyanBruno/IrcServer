package com.rbruno.irc.commands;

import java.util.ArrayList;

public class Command {

	private String command;
	private int parameters;

	private static ArrayList<Command> commands = new ArrayList<Command>();

	public static void add() {

	}

	public Command(String command, int parameters) {
		this.command = command;
		this.parameters = parameters;
	}

	public void execute() {
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
		for (Command curret : commands) {
			if (curret.getCommand().equalsIgnoreCase(command)) return curret;
		}
		return null;
	}

}
