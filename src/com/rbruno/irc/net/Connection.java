package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.rbruno.irc.Request;
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
					//TODO: Send ERR_UNKNOWNCOMMAND
				}
				Command.getCommand(request.getCommand()).execute(request);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String prefix, int code, String targetNickname, String args) throws IOException {
		String message = ":" + prefix + " " + code + " " + targetNickname + " " + args;
		byte[] block = message .getBytes();
		
		socket.getOutputStream().write(block);
		socket.getOutputStream().flush();
	}

	public void close() {
		open = false;
	}
	
	public Socket getSocket(){	
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