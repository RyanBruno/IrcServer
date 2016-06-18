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
				try {
					String line = reader.readLine();
					Logger.log(line, Level.FINE);
					if (line == null) {
						close();
						continue;
					}
					Request request = new Request(this, line);
					Command.runCommand(request);
				} catch (Exception e) {
					if (e.getMessage() != null) {
						if (e.getMessage().contains("Connection reset")) {
							this.close();
							continue;
						}
					}
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
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
		System.out.println("[DeBug]" + message);
		byte[] block = message.concat("\r\n").getBytes();

		socket.getOutputStream().write(block);
		socket.getOutputStream().flush();
	}

	public void send(String prefix, String command, String args) throws IOException {
		String message = ":" + prefix + " " + command + " " + args;
		send(message);
	}

	public void send(String prefix, String code, String nickname, String args) throws IOException {
		String message = ":" + prefix + " " + code + " " + nickname + " " + args;
		send(message);
	}

	public void send(int code, String nickname, String args) throws IOException {
		String stringCode = code + "";
		if (stringCode.length() < 2) stringCode = "0" + stringCode;
		if (stringCode.length() < 3) stringCode = "0" + stringCode;

		send(Server.getServer().getConfig().getProperty("hostname"), stringCode, nickname, args);
	}

	public void send(Reply reply, String nickname, String args) throws IOException {
		send(reply.getCode(), nickname, args);
	}

	public void send(Reply reply, Client client, String args) throws IOException {
		send(reply, client.getNickname(), args);
	}

	public void send(Error error, String nickname, String args) throws IOException {
		send(error.getCode(), nickname, args);
	}

	public void send(Error error, Client client, String args) throws IOException {
		send(error, client.getNickname(), args);
	}

	public void close() throws IOException {
		socket.close();
		Server.getServer().getClientManager().removeClient(client);
		open = false;
	}

	public Socket getSocket() {
		return socket;
	}

	public String getConnectionPassword() {
		return connectionPassword;
	}

	public void setConnectionPassword(String connectionPassword) {
		this.connectionPassword = connectionPassword;
	}

	public boolean isClient() {
		return type == Type.CLIENT;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		type = Type.CLIENT;
		this.client = client;
	}

	public boolean isServer() {
		return type == Type.SERVER;
	}

	public Type getType() {
		return type;
	}

}