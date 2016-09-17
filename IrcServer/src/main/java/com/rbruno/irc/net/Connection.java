package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;

import com.rbruno.irc.Server;
import com.rbruno.irc.commands.Command;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.reply.Error;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Request;

/**
 * An object that listens to a socket and process its requests.
 */
public class Connection implements Runnable {

	private Socket socket;
	private boolean open = true;

	private String connectionPassword;

	private Client client;

	private Type type = Type.LOGGIN_IN;

	/**
	 * Creates a new Connection object. Will not start listing to socket until
	 * Connection.run() is called.
	 * 
	 * @param socket
	 * @see Connection.run()
	 */
	public Connection(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Start listing on socket. When a line is received it creates a new Request
	 * object and send to Command.runCommnd(Request)
	 */
	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while (open) {
				String line = null;
				try {
					line = reader.readLine();
				} catch (Exception e) {
					if (e.getMessage() != null) if (e.getMessage().contains("Connection reset")) {
						this.close();
						continue;
					}
				}
				if (Server.getServer().getConfig().getProperty("debug").equals("true")) Logger.log(line, Level.FINE);
				if (line == null) {
					close();
					continue;
				}
				Request request = null;
				try {
					request = new Request(this, line);
				} catch (Exception e) {
					Logger.log(socket.getInetAddress() + " sent an unparseable line: " + line, Level.FINE);
					continue;
				}
				try {
					Command.runCommand(request);
				} catch (Exception e) {
					Logger.log(socket.getInetAddress() + " ran a command that resulted in an error: " + line, Level.FINE);
					e.printStackTrace();
				}

			}
			reader.close();
		} catch (IOException e) {
			Logger.log(socket.getInetAddress() + " closed in an error state.", Level.FINE);
			this.close();
		}
	}

	public enum Type {
		LOGGIN_IN, SERVER, CLIENT
	}

	/**
	 * Sends message to the socket
	 * 
	 * @param message
	 *            Message to be sent to connection.
	 * @throws IOException
	 */
	public void send(String message) throws IOException {
		if (Server.getServer().getConfig().getProperty("debug").equals("true")) System.out.println("[DeBug]" + message);
		byte[] block = message.concat("\r\n").getBytes();

		socket.getOutputStream().write(block);
		socket.getOutputStream().flush();
	}

	/**
	 * Creates a formated string from the given arguments and passes them to
	 * send(String). EX: :(prefix) (command) (args)
	 * 
	 * @param prefix
	 *            The prefix to be used in the message.
	 * @param command
	 *            The command to be used in the message.
	 * @param args
	 *            The args to be used in the message.
	 * @throws IOException
	 */
	public void send(String prefix, String command, String args) throws IOException {
		String message = ":" + prefix + " " + command + " " + args;
		send(message);
	}

	/**
	 * Creates a formated string from the given arguments and passes them to
	 * send(String).
	 * 
	 * @param code
	 *            The code to be used in the message.
	 * @param nickname
	 *            The nickname to be used in the message.
	 * @param args
	 *            The arguments to be used in the message.
	 * @throws IOException
	 */
	public void send(int code, String nickname, String args) throws IOException {
		String stringCode = code + "";
		if (stringCode.length() < 2) stringCode = "0" + stringCode;
		if (stringCode.length() < 3) stringCode = "0" + stringCode;

		String message = ":" + Server.getServer().getConfig().getProperty("hostname") + " " + stringCode + " " + nickname + " " + args;
		send(message);
	}

	/**
	 * Passes arguments to send(int, String, String).
	 * 
	 * @param reply
	 *            The reply to be used in the message.
	 * @param nickname
	 *            The nickname to be used in the message.
	 * @param args
	 *            The args to be used in the message.
	 * @throws IOException
	 */
	public void send(Reply reply, String nickname, String args) throws IOException {
		send(reply.getCode(), nickname, args);
	}

	/**
	 * Passes arguments to send(int, String, String).
	 * 
	 * @param reply
	 *            The reply to be used in the message.
	 * @param client
	 *            The client to be used in the message.
	 * @param args
	 *            The args to be used in the message.
	 * @throws IOException
	 */
	public void send(Reply reply, Client client, String args) throws IOException {
		send(reply.getCode(), client.getNickname(), args);
	}

	/**
	 * Passes arguments to send(int, String, String).
	 * 
	 * @param error
	 *            The error to be used in the message.
	 * @param nickname
	 *            The nickname to be used in the message.
	 * @param args
	 *            The args to be used in the message.
	 * @throws IOException
	 */
	public void send(Error error, String nickname, String args) throws IOException {
		send(error.getCode(), nickname, args);
	}

	/**
	 * Passes arguments to send(int, String, String).
	 * 
	 * @param error
	 *            The error to be used in the message.
	 * @param client
	 *            The client to be used in the message.
	 * @param args
	 *            The args to be used in the message.
	 * @throws IOException
	 */
	public void send(Error error, Client client, String args) throws IOException {
		send(error.getCode(), client.getNickname(), args);
	}

	/**
	 * Closes the socket and removes the removes the client from the
	 * ClientManager.
	 */
	public void close() {
		open = false;
		try {
			socket.close();
			if (client != null) Server.getServer().getClientManager().removeClient(client);
		} catch (IOException e) {
		}
	}

	/**
	 * Returns the Socket.
	 * 
	 * @return The Socket.
	 */
	public Socket getSocket() {
		return socket;
	}

	/**
	 * Returns the connection password or "" is none was given.
	 * 
	 * @return The connection password
	 */
	public String getConnectionPassword() {
		if (connectionPassword == null) return "";
		return connectionPassword;
	}

	/**
	 * Sets the connection password.
	 * 
	 * @param connectionPassword
	 *            The password for the connection.
	 */
	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	/**
	 * Returns weather or not the connection is a client.
	 * 
	 * @return Weather or not the connection is a client.
	 */
	public boolean isClient() {
		return type == Type.CLIENT;
	}

	/**
	 * Returns the Client or null if one is not set.
	 * 
	 * @return The Client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * Sets the Client and sets the connection type to Client.
	 * 
	 * @param client
	 *            The Client this connection is connected to.
	 */
	public void setClient(Client client) {
		type = Type.CLIENT;
		this.client = client;
	}

	/**
	 * Returns weather or not the connection is a Server.
	 * 
	 * @return Weather or not the connection is a Server.
	 */
	public boolean isServer() {
		return type == Type.SERVER;
	}

	/**
	 * Returns what type of connection it is.
	 * @return What type of connection it is.
	 */
	public Type getType() {
		return type;
	}

}