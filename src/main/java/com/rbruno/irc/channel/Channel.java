package com.rbruno.irc.channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.reply.Reply;

/**
 * An object that stores all the information on a Channel including its clients.
 */
public class Channel {

	private Server server;

	private String name;
	private String password;
	private ArrayList<Client> clients = new ArrayList<Client>();
	private HashMap<Character, String> modes = new HashMap<Character, String>();
	private String topic = "";

	/**
	 * Creates a new a new Channels object. Will not add to ChannelManger.
	 * 
	 * @param name
	 *            Name of channel should start with '#' or '&'.
	 * @param password
	 *            Password of channel. If blank then no password is needed.
	 * @see ChannelManger.addChannel(Channel)
	 */
	public Channel(String name, String password, Server server) {
		this.name = name;
		this.password = password;
		this.server = server;
	}

	/**
	 * Returns weather or not a mode is set.
	 * 
	 * @param mode
	 * @return True if mode is set false if not.
	 */
	public boolean isMode(char mode) {
		return modes.containsKey(mode);
	}

	public void setMode(char mode, boolean add, String string) {
		if (add) {
			modes.put(mode, string);
		} else {
			modes.remove(mode);
		}
	}

	public String getMode(char mode) {
		return modes.get(mode);
	}

	/**
	 * Sends a reply to all clients on channel.
	 * 
	 * @param reply
	 *            Reply code to use.
	 * @param message
	 *            Message to send.
	 * @throws IOException
	 */
	public void send(Reply reply, String message) throws IOException {
		for (Client current : clients)
			current.getConnection().send(reply, current, message);
	}

	/**
	 * Sends a PRIVMSG to all clients on channel.
	 * 
	 * @param sender
	 *            Who sent the message.
	 * @param message
	 *            Message to be sent.
	 * @throws IOException
	 */
	public void sendMessage(Client sender, String message) throws IOException {
		for (Client current : clients)
			if (current != sender) send(current, ":" + sender.getAbsoluteName() + " PRIVMSG " + this.getName() + " " + message);
	}

	private void send(Client target, String message) throws IOException {
		target.getConnection().send(message);
	}

	private void sendToAll(String message) throws IOException {
		for (Client current : clients)
			current.getConnection().send(message);
	}

	/**
	 * Returns the channel's name.
	 * 
	 * @return The name of the channel.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Adds client to channel. Sends the client a NAMES command and sends a JOIN
	 * message to all clients.
	 * 
	 * @param client
	 *            Client to be added to the channel.
	 * @throws IOException
	 */
	public void addClient(Client client) throws IOException {
		clients.add(client);

		this.sendToAll(":" + client.getNickname() + "!" + client.getUsername() + "@" + client.getHostname() + " JOIN " + this.getName());
		if (this.checkOP(client)) {
			this.send(Reply.RPL_CHANNELMODEIS, this.getName() + " +o " + client.getNickname());
		} else if (this.hasVoice(client)) {
			this.send(Reply.RPL_CHANNELMODEIS, this.getName() + " +v " + client.getNickname());
		}

		String message = "@ " + this.getName() + " :";
		ArrayList<Client> clients = this.getClients();
		for (Client current : clients) {
			if (this.checkOP(current)) {
				message = message + "@" + current.getNickname() + " ";
			} else if (this.hasVoice(current)) {
				message = message + "+" + current.getNickname() + " ";
			} else {
				message = message + current.getNickname() + " ";
			}
		}
		client.getConnection().send(Reply.RPL_NAMREPLY, client, message);
		client.getConnection().send(Reply.RPL_ENDOFNAMES, client, this.getName() + " :End of /NAMES list.");
	}

	public void removeClient(Client client) {
		clients.remove(client);
		if (this.clients.size() == 0) server.getChannelManger().removeChannel(this);

	}

	/**
	 * Returns an ArrayList of all Clients currently in this Channel.
	 * 
	 * @return An ArrayList of all Clients currently in this Channel.
	 */
	public ArrayList<Client> getClients() {
		return clients;
	}

	/**
	 * Returns weather or not the password given matches the channel password.
	 * Returns true if no Channel Password is set.
	 * 
	 * @param password
	 *            Password to test.
	 * @return Weather or not the password given matches the channel password or
	 *         true if no Channel Password is set.
	 */
	public boolean checkPassword(String password) {
		if (this.password == null || this.password.equals("")) return true;
		return this.password.equals(password);
	}

	/**
	 * Sets the Channel Password.
	 * 
	 * @param password
	 *            The new Channel Password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns true if Client is a ChanOp.
	 * 
	 * @param client
	 *            Client to check.
	 * @return True if Client is a ChanOp. False if not.
	 */
	public boolean checkOP(Client client) {
		if (modes.get('o') == null) return false;
		for (String nick : modes.get('o').split(","))
			if (nick.equals(client.getNickname())) return true;
		return false;
	}

	/**
	 * Adds a ChanOP.
	 * 
	 * @param client
	 *            Client to make ChanOp.
	 */
	public void addOP(Client client) {
		if (modes.get('o') == null) modes.put('o', "");
		modes.put('o', modes.get('o') + client.getNickname() + ",");
	}

	/**
	 * Removes a ChanOp.
	 * 
	 * @param client
	 *            Client to take ChanOp.
	 */
	public void takeOP(Client client) {
		if (modes.get('o') == null) return;
		modes.put('o', modes.get('o').replaceAll(client.getNickname() + ",", ""));

	}

	/**
	 * Returns the current amount of users in this Channel.
	 * 
	 * @return The current amount of users in this Channel.
	 */
	public int getUsersCount() {
		return clients.size();
	}

	/**
	 * Gives voice to a user.
	 * 
	 * @param voicee
	 *            User that now has a voice.
	 */
	public void giveVoice(Client voicee) {
		if (modes.get('v') == null) modes.put('v', "");
		modes.put('v', modes.get('v') + voicee.getNickname() + ",");
	}

	/**
	 * Takes voice from a user.
	 * 
	 * @param voicee
	 *            User that no longer has a voice.
	 */
	public void takeVoice(Client voicee) {
		if (modes.get('v') == null) return;
		modes.put('v', modes.get('v').replaceAll(voicee.getNickname() + ",", ""));
	}

	/**
	 * Checks if a user has a voice.
	 * 
	 * @param voicee
	 *            User to check if has a voice.
	 * @return True if the given user has a voice. False if not.
	 */
	public boolean hasVoice(Client voicee) {
		if (modes.get('v') == null) return false;
		for (String nick : modes.get('v').split(","))
			if (nick.equals(voicee.getNickname())) return true;
		return false;
	}

	/**
	 * Return the Channel topic.
	 * 
	 * @return THe Channel topic.
	 */
	public String getTopic() {
		if (topic.equals("") || topic == null) return "Default Topic";
		return topic;
	}

	/**
	 * Sets topic and tell all clients on channel of the change.
	 * 
	 * @param topic
	 *            New topic.
	 * @throws IOException
	 */
	public void setTopic(String topic) throws IOException {
		this.topic = topic;
		for (Client current : clients)
			current.getConnection().send(Reply.RPL_TOPIC, current, this.getName() + " " + this.getTopic());
	}

	/**
	 * Checks if a user is currents on this Channel
	 * 
	 * @param client
	 *            User to check.
	 * @return True if user is on this Channel. False if not.
	 */
	public boolean isUserOnChannel(Client client) {
		return clients.contains(client);
	}

	/**
	 * Returns a HashMap of all the ChannelModes and weather they are enabled or
	 * not.
	 * 
	 * @return A HashMap of all the ChannelModes and weather they are enabled or
	 *         not.
	 */
	public HashMap<Character, String> getModeMap() {
		return modes;
	}

}
