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
	private ArrayList<Client> ops = new ArrayList<Client>();
	private HashMap<ChannelMode, Boolean> modes = new HashMap<ChannelMode, Boolean>();
	private ArrayList<Client> voiceList = new ArrayList<Client>();
	private ArrayList<Client> invitedUsers = new ArrayList<Client>();
	private int userLimit = 100;
	private String topic = "";
	private boolean temporary;

	/**
	 * Creates a new a new Channels object. Will not add to ChannelManger.
	 * 
	 * @param name
	 *            Name of channel should start with '#' or '&'.
	 * @param password
	 *            Password of channel. If blank then no password is needed.
	 * @see ChannelManger.addChannel(Channel)
	 */
	public Channel(String name, String password, boolean temporary, Server server) {
		this.name = name;
		this.password = password;
		this.temporary = temporary;
		this.server = server;
	}

	public enum ChannelMode {
		PRIVATE("p"), SECRET("s"), INVITE_ONLY("i"), TOPIC("t"), NO_MESSAGE_BY_OUTSIDE("n"), MODERATED_CHANNEL("m");
		private String symbol;

		ChannelMode(String symbol) {
			this.symbol = symbol;
		}

		public String getSymbol() {
			return symbol;
		}
	}

	/**
	 * Returns weather or not a mode is set.
	 * 
	 * @param mode
	 * @return True if mode is set false if not.
	 */
	public boolean getMode(ChannelMode mode) {
		if (!modes.containsKey(mode)) return false;
		return modes.get(mode);
	}

	/**
	 * Sets mode to add. Also tells all users of the new change.
	 * 
	 * @param mode
	 *            ChannelMode to be set.
	 * @param add
	 *            What mode should be set to.
	 * @param sender
	 *            Who requested this mode change. To be used when sending to all
	 *            Clients.
	 * @throws IOException
	 */
	public void setMode(ChannelMode mode, boolean add, Client sender) throws IOException {
		send(Reply.RPL_CHANNELMODEIS, sender.getAbsoluteName() + " sets mode " + (add ? "+" : "-") + mode.getSymbol() + " on " + name);
		modes.put(mode, add);
	}

	/**
	 * Sets mode to add.
	 * 
	 * @param mode
	 *            ChannelMode to be set.
	 * @param add
	 *            What mode should be set to.
	 * @throws IOException
	 */
	public void setMode(ChannelMode mode, boolean add) throws IOException {
		modes.put(mode, add);
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
		if (!this.getMode(ChannelMode.MODERATED_CHANNEL) || client.isServerOP()) this.voiceList.add(client);

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
		voiceList.remove(client);
		clients.remove(client);
		if (this.clients.size() == 0 && this.isTemporary() && server.getConfig().getProperty("RemoveChannelOnEmpty").equals("true")) {
			server.getChannelManger().removeChannel(this);
		}
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
		return ops.contains(client);
	}

	/**
	 * Adds a ChanOP.
	 * 
	 * @param client
	 *            Client to make ChanOp.
	 */
	public void addOP(Client client) {
		ops.add(client);
	}

	/**
	 * Removes a ChanOp.
	 * 
	 * @param client
	 *            Client to take ChanOp.
	 */
	public void takeOP(Client client) {
		ops.remove(client);
	}

	/**
	 * Sets Channel User Limit.
	 * 
	 * @param limit
	 *            New Channel user limit.
	 */
	public void setUserLimit(int limit) {
		this.userLimit = limit;
	}

	/**
	 * Returns the user limit.
	 * 
	 * @return The user limit.
	 */
	public int getUserLimit() {
		return userLimit;
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
		voiceList.add(voicee);
	}

	/**
	 * Takes voice from a user.
	 * 
	 * @param voicee
	 *            User that no longer has a voice.
	 */
	public void takeVoice(Client voicee) {
		voiceList.remove(voicee);
	}

	/**
	 * Checks if a user has a voice.
	 * 
	 * @param voicee
	 *            User to check if has a voice.
	 * @return True if the given user has a voice. False if not.
	 */
	public boolean hasVoice(Client voicee) {
		return voiceList.contains(voicee);
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
	 * Invites a user to the Channel.
	 * 
	 * @param client
	 *            User to invite to the Channel.
	 */
	public void inviteUser(Client client) {
		this.invitedUsers.add(client);
	}

	/**
	 * Uninvites a user to the Channel.
	 * 
	 * @param client
	 *            User to uninvite to the Channel.
	 */
	public void unInviteUser(Client client) {
		this.invitedUsers.remove(client);
	}

	/**
	 * Checks if a user is invited to this Channel.
	 * 
	 * @param client
	 *            User to check.
	 * @return True if given user is invited. False if not.
	 */
	public boolean isUserInvited(Client client) {
		return invitedUsers.contains(client);
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
	public HashMap<ChannelMode, Boolean> getModeMap() {
		return modes;
	}

	/**
	 * Returns weather or not this Channel is temporary or not.
	 * 
	 * @return Weather or not this Channel is temporary or not.
	 */
	public boolean isTemporary() {
		return temporary;
	}

}
