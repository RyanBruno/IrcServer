package com.rbruno.irc;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import com.rbruno.irc.commands.Command;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.manage.ChannelManager;
import com.rbruno.irc.manage.ClientManager;
import com.rbruno.irc.net.Connection;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.util.Utilities;

/**
 * Contains main method. The main class creates a new Server object which starts
 * everything up.
 */
public class Server implements Runnable {

	private static final String VERSION = "v0.12-SNAPSHOT";

	private static Server server;

	private boolean running;
	private ServerSocket serverSocket;

	private Config config;
	private ClientManager clientManager;
	private ChannelManager channelManger;

	/**
	 * Server constructor. Starts all managers, opens the socket and starts the
	 * running thread.
	 * 
	 * @throws Exception
	 */
	public Server() throws Exception {
		try {
			config = new Config();
		} catch (Exception e) {
			Logger.log("There has been a fatal error while parsing the config.", Level.SEVERE);
			throw e;
		}
		clientManager = new ClientManager();
		try {
			channelManger = new ChannelManager();
		} catch (Exception e) {
			Logger.log("There has been a fatal error while parsing the channels file.", Level.SEVERE);
			throw e;
		}
		server = this;

		Command.init();
		try {
			serverSocket = new ServerSocket(Integer.parseInt(config.getProperty("port")));
		} catch (Exception e) {
			Logger.log("There has been a fatal error while opening the Server socket. Check if a server is already using port: " + config.getProperty("port"), Level.SEVERE);
			throw e;
		}
		running = true;
		Logger.log("Started Server on port: " + serverSocket.getLocalPort());
		new Thread(this, "Running Thread").start();
	}

	/**
	 * Main running thread. Waits for sockets then creates a new Connection
	 * object on a new thread.
	 */
	public void run() {
		while (running) {
			try {
				Socket socket = serverSocket.accept();
				Thread connection = new Thread(new Connection(socket));
				connection.start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sends MOTD to client. Reads from motd.txt. If not found will create one.
	 * 
	 * @param client
	 *            Client to send MOTD.
	 * @throws IOException
	 */
	public void sendMOTD(Client client) throws IOException {
		client.getConnection().send(Reply.RPL_MOTDSTART, client, ":- " + getConfig().getProperty("hostname") + " Message of the day - ");
		File motd = new File("motd.txt");
		if (!motd.exists()) Utilities.makeFile("motd.txt");
		for (String line : Utilities.read("motd.txt"))
			client.getConnection().send(Reply.RPL_MOTD, client, ":- " + line);
		client.getConnection().send(Reply.RPL_ENDOFMOTD, client, ":End of /MOTD command");
	}

	public static void main(String args[]) throws Exception {
		new Server();
	}

	/**
	 * Gets current Server instance.
	 * 
	 * @return Server instance.
	 */
	public static Server getServer() {
		return server;
	}

	/**
	 * Gets Config object.
	 * 
	 * @return Config.
	 */
	public Config getConfig() {
		return config;
	}

	/**
	 * Returns the ClientManager.
	 * 
	 * @return ClientManager.
	 */
	public ClientManager getClientManager() {
		return clientManager;
	}

	/**
	 * Returns the ChannelManager.
	 * 
	 * @return ChannelManager.
	 */
	public ChannelManager getChannelManger() {
		return channelManger;
	}

	/**
	 * Returns current server version.
	 * 
	 * @return Current server version.
	 */
	public static String getVersion() {
		return VERSION;
	}

}