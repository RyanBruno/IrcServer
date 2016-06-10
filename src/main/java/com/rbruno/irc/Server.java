package com.rbruno.irc;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.rbruno.irc.commands.Command;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.manage.ChannelManager;
import com.rbruno.irc.manage.ClientManager;
import com.rbruno.irc.net.Connection;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.util.Utilities;

public class Server implements Runnable {

	private static final String VERSION = "v0.10-SNAPSHOT";

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
		Logger.log("Started Server on port: " + serverSocket.getLocalPort());
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

	public static String getVersion() {
		return VERSION;
	}

	public void sendMOTD(Client client) throws IOException {
		client.getConnection().send(Reply.RPL_MOTDSTART, client, ":- " + getConfig().getProperty("hostname") + " Message of the day - ");
		File motd = new File("motd.txt");
		if (!motd.exists())
			Utilities.makeFile("motd.txt");
		for (String line : Utilities.read("motd.txt"))
			client.getConnection().send(Reply.RPL_MOTD, client, ":- " + line);
		client.getConnection().send(Reply.RPL_MOTD, client, ":- ");
		client.getConnection().send(Reply.RPL_ENDOFMOTD, client, ":End of /MOTD command");

	}

}