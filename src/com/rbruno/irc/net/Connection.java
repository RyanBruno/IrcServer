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
	private AdjacentServer adjacentServer;
	private boolean isClient = true;

	public Connection(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while (open) {
				String line = reader.readLine();
				Request request = new Request(this, line);
				if (!Command.isCommand(request.getCommand())) {
					// this.send(prefix, code, targetNickname, args)
				}
				try {
					Command.runCommand(request.getCommand(), request);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send(String prefix, int code, String targetNickname, String args) throws IOException {
		String message = ":" + prefix + " " + code + " " + targetNickname + " " + args;
		byte[] block = message.getBytes();

		socket.getOutputStream().write(block);
		socket.getOutputStream().flush();
	}

	public void send(int code, String nickname, String args) throws IOException {
		send(Server.getServer().getConfig().getProperty("servername"), code, nickname, args);
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

	public void close() {
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
		return isClient;
	}

	public AdjacentServer getAdjacentServer() {
		return adjacentServer;
	}

	public void setAdjacentServer(AdjacentServer adjacentServer) {
		this.adjacentServer = adjacentServer;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		isClient = true;
		this.client = client;
	}

}