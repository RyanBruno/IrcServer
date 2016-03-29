package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.rbruno.irc.Error;
import com.rbruno.irc.Reply;
import com.rbruno.irc.Request;
import com.rbruno.irc.Server;
import com.rbruno.irc.commands.Command;

public class Connection implements Runnable {

	private Socket socket;
	private boolean open = true;

	private String connectionPassword;

	private Client client;
	
	private Type type = Type.LOGGIN_IN;

	public Connection(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while (open) {
				String line = reader.readLine();
				Request request = new Request(this, line);
				try {
					Command.runCommand(request);
				} catch (Exception e) {
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
	
	public void send(String message) throws IOException {
		byte[] block = message.getBytes();

		socket.getOutputStream().write(block);
		socket.getOutputStream().flush();
	}
	
	public void send(String prefix, String command, String args) throws IOException {
		String message = ":" + prefix + " " + command  + " " + args;
		send(message);
	}

	public void send(String prefix, int code, String targetNickname, String args) throws IOException {
		String message = ":" + prefix + " " + code + " " + targetNickname + " " + args;
		send(message);
	}

	public void send(int code, String name, String args) throws IOException {
		send(Server.getServer().getConfig().getProperty("servername"), code, name, args);
	}

	public void send(Reply reply, String nickname, String args) throws IOException {
		send(reply.getCode(), nickname, args);
	}

	public void send(Reply reply, Client client, String args) throws IOException {
		send(reply, client.getNickname(), args);
	}

	public void send(Error error, String name, String args) throws IOException {
		send(error.getCode(), name, args);
	}

	public void send(Error error, Client client, String args) throws IOException {
		System.out.println(client);
		send(error, client.getNickname(), args);
	}

	public void close() {
		//TODO Fix later
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