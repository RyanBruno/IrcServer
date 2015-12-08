package com.rbruno.irc.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import com.rbruno.irc.Request;

public class Manager implements Runnable {

	private Socket socket;
	private boolean open = true;

	private String nick;

	private Client client;
	private AdjacentServer server;
	private boolean isServer = false;

	public Manager(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
			while (open) {
				String line = reader.readLine();
				Request request = new Request(line);
				this.process(request);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void process(Request request) {
		// TODO: Process the request
	}

	public void close() {
		open = false;
	}

	public Client getClient() {
		return client;
	}

	public AdjacentServer getServer() {
		return server;
	}

	public boolean isServer() {
		return isServer;
	}

	public String getNick() {
		return nick;
	}

}