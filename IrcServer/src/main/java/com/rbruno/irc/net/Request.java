package com.rbruno.irc.net;

/**
 * Phrases and stores information on a request made by a client.
 */
public class Request {

	private Connection connection;
	private String prefix;
	private String command;
	private String[] args;

	private boolean cancelled;

	/**
	 * Creates a new Request object. Phrases the line into prefix, command and
	 * arguments.
	 * 
	 * @param connection
	 *            Connection the request came from.
	 * @param line
	 *            The line that was sent.
	 * @throws Exception
	 */
	public Request(Connection connection, String line) throws Exception {
		this.connection = connection;
		if (line.startsWith(":")) {
			this.prefix = line.split(" ")[0].substring(1);
			line = line.substring(prefix.length() + 1);
		}
		this.command = line.split(" ")[0];
		line = line.substring(command.length() + 1);
	
		this.args = line.split(":")[0].split(" ");
		if (line.split(":").length > 1) {
			String[] newArray = new String[args.length + 1];
			for (int i = 0; i < args.length; i++)
				newArray[i] = args[i];
			newArray[newArray.length - 1] = line.split(":")[1];
			args = newArray;
		}
	}

	/**
	 * Returns the connection that the request was sent through.
	 * 
	 * @return The connection that the request was sent through.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Returns the prefix of the that was sent. The prefix should only be used
	 * when sent by server.
	 * 
	 * @return The prefix of the that was sent.
	 */
	public String getPrefix() {
		return prefix;
	}

	/**
	 * Returns the command that was sent.
	 * 
	 * @return The command that was sent.
	 */
	public String getCommand() {
		return command;
	}

	/**
	 * Returns the arguments that were sent.
	 * 
	 * @return The array of arguments.
	 */
	public String[] getArgs() {
		return args;
	}

	/**
	 * Return weather or not this request is cancelled. When a request is
	 * cancelled it will not be run by the server.
	 * 
	 * @return Weather or not this request is cancelled.
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the cancelled status of this request. When a request is cancelled it
	 * will not be run by the server.
	 * 
	 * @param cancelled
	 *            Cancelled status.
	 */
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

}