package com.rbruno.irc;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;

import com.rbruno.irc.channel.ChannelManager;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.client.ClientManager;
import com.rbruno.irc.commands.client.ClientCommand;
import com.rbruno.irc.commands.registration.RegCommand;
import com.rbruno.irc.config.Config;
import com.rbruno.irc.logger.Logger;
import com.rbruno.irc.net.Connection;
import com.rbruno.irc.plugin.PluginManager;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.util.Utilities;

/**
 * Contains main method. The main class creates a new Server object which starts
 * everything up.
 */
public class Server {

	private static final String VERSION = "v1.0-RELEASE";

	private ServerSocket serverSocket;

	private Config config;
	private ClientManager clientManager;
	private ChannelManager channelManger;
	private PluginManager pluginManager;
	
	private static Server server;

	/**
	 * Server constructor. Starts all managers, opens the socket and starts the
	 * running thread.
	 * @param bootStrap 
	 * 
	 * @throws Exception
	 */
	public Server(ServerBootStrap bootStrap) throws Exception {
	  server = this;
	  config = bootStrap.createConfig();
	  clientManager = bootStrap.createClientManager();
	  channelManger = bootStrap.createChannelManager();
	  pluginManager = bootStrap.createPluginManager();
	  
		RegCommand.init();
		ClientCommand.init();
		
		serverSocket = new ServerSocket(config.getPort());
		run();

		Logger.log("Started Server on port: " + serverSocket.getLocalPort());
	}

	/**
	 * Main running thread. Waits for sockets then creates a new Connection
	 * object on a new thread.
	 */
	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				Thread connection = new Thread(new Connection(socket, this));
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
		client.getConnection().send(Reply.RPL_MOTDSTART, client, ":- " + config.getHostname() + " Message of the day - ");
		File motd = new File("motd.txt");
		if (!motd.exists()) Utilities.makeFile("motd.txt");
		for (String line : Utilities.read("motd.txt"))
			client.getConnection().send(Reply.RPL_MOTD, client, ":- " + line);
		client.getConnection().send(Reply.RPL_ENDOFMOTD, client, ":End of /MOTD command");
	}

	public static void main(String args[]) throws Exception {
		ServerBootStrap bootStrap = new IrcBootstrap();
		new Server(bootStrap);
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

	/**
	 * Returns the PluginManager
	 * 
	 * @return PluginManager
	 */
	public PluginManager getPluginManager() {
		return pluginManager;
	}
	
	public static Server getServer() {
	  return server;
	}
}
