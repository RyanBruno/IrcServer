package com.rbruno.irc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.rbruno.irc.commands.Command;
import com.rbruno.irc.net.Connection;

public class Server implements Runnable {

	private static Server server;

	private boolean running;
	private ServerSocket serverSocket;

	private Config config;
	private ClientManager clientManager;
	private ChannelManager channelManger;

	public Server() throws Exception {
		config = new Config();
		clientManager = new ClientManager();
		channelManger = new ChannelManager();		
		server = this;
		
		Command.init();
		serverSocket = new ServerSocket(Integer.parseInt(config.getProperty("port")));
		Thread run = new Thread(this, "Running Thread");
		running = true;
		run.start();
	}

	public void run() {
		while (running) {
			Socket socket;
			try {
				socket = serverSocket.accept();
				Thread connection = new Thread(new Connection(socket));
				connection.run();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	

	public static void main(String args[]) throws Exception {
		new Server();
	}

	public static Server getServer() {
		return server;
	}

	public Config getConfig() {
		return config;
	}

	public ClientManager getClientManager() {
		return clientManager;
	}

	public ChannelManager getChannelManger() {
		return channelManger;
	}

}