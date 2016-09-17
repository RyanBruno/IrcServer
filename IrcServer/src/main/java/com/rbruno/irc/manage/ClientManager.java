package com.rbruno.irc.manage;

import java.io.IOException;
import java.util.ArrayList;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Client.ClientMode;

/**
 * Manages all clients local and remote.
 */
public class ClientManager {

	private ArrayList<Client> clients = new ArrayList<Client>();

	/**
	 * Sends a message to all clients connected that are directly connected to
	 * this server.
	 * 
	 * @param reply
	 * @param args
	 * @throws IOException
	 */
	public void broadcastLocal(Reply reply, String args) throws IOException {
		for (Client client : this.getClients())
			if (client.getConnection().isClient()) client.getConnection().send(reply, client, args);
	}

	/**
	 * Sends a message to all clients connected that are directly connected to
	 * this server.
	 * 
	 * @param prefix
	 *            The prefix with out a preceding :
	 * @param command
	 * @param args
	 * @throws IOException
	 */
	public void broadcastLocal(String prefix, String command, String args) throws IOException {
		for (Client client : this.getClients())
			if (client.getConnection().isClient()) client.getConnection().send(prefix, command, args);
	}

	/**
	 * Adds client to array. Will not check for nick names.
	 * 
	 * @param client
	 *            Client to add.
	 * @throws Exception
	 */
	public void addClient(Client client) {
		clients.add(client);
		Server.getServer().getPluginManager().runOnClientLogin(client);
	}

	/**
	 * Returns Client object the matches the given nickname. Returns null is no
	 * client has the given nickname.
	 * 
	 * @param nickname
	 *            Nickname of client that is being requested.
	 * @return Client that matches given nickname. Returns null if no client has
	 *         that nickname.
	 */
	public Client getClient(String nickname) {
		for (Client client : this.getClients())
			if (client.getNickname().equals(nickname)) return client;
		return null;
	}

	/**
	 * Removes client from clients array.
	 * 
	 * @param client
	 *            Client to be removed.
	 */
	public void removeClient(Client client) {
		clients.remove(client);
	}

	/**
	 * Removes Client that has given nickname from array.
	 * 
	 * @param nickname
	 *            Nickname of client that will be removed.
	 */
	public void removeClient(String nickname) {
		for (Client client : this.getClients())
			if (client.getNickname().equals(nickname)) removeClient(client);
	}

	/**
	 * Returns weather or not a Client with given nickname exists.
	 * 
	 * @param nickname
	 * @return True if client exists false if not.
	 */
	public boolean isNick(String nickname) {
		for (Client client : this.getClients())
			if (client.getNickname().equals(nickname)) return true;
		return false;
	}

	/**
	 * Returns the amount of clients that are not invisible.
	 * 
	 * @return The amount of Clients that are not invisible.
	 */
	public int getClientCount() {
		int users = 0;
		for (Client current : this.getClients())
			if (!current.hasMode(ClientMode.INVISIBLE)) users++;
		return users;
	}

	/**
	 * Returns the amount of clients that are invisible.
	 * 
	 * @return The amount of Clients that are invisible.
	 */
	public int getInvisibleClientCount() {
		int users = 0;
		for (Client current : this.getClients())
			if (current.hasMode(ClientMode.INVISIBLE)) users++;
		return users;
	}

	/**
	 * Returns the amount of Server Operators online.
	 * 
	 * @return The amount of Server Operators online.
	 */
	public int getOps() {
		int ops = 0;
		for (Client current : this.getClients())
			if (current.isServerOP()) ops++;
		return ops;
	}

	private ArrayList<Client> getClients() {
		return new ArrayList<Client>(clients);
	}
}
