package com.rbruno.irc.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.net.ClientConnection;
import com.rbruno.irc.reply.Reply;

/**
 * Stores information on a client local and remote.
 */
public class Client {

	private ClientConnection connection;
	private String nickname;
	private String username;
	private String hostname;
	private String servername;
	private String realName;

	private long lastCheckin;

	private ArrayList<Channel> channels = new ArrayList<Channel>();
	private HashMap<ClientMode, Boolean> modes = new HashMap<ClientMode, Boolean>();
	private String awayMessage = "";

	/**
	 * Creates a new Client object. Will not add to ClientManager.
	 * 
	 * @param connection
	 *            The client's connection.
	 * @param nickname
	 *            Nickname of the client.
	 */
	/*public Client(Connection connection, String nickname) {
		this.connection = connection;
		this.nickname = nickname;
	}*/

	public Client(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 
	 * Creates a new Client object. Will not add to ClientManager.
	 * 
	 * @param connection
	 *            The client's connection.
	 * @param nickname
	 *            Nickname of the client.
	 * @param username
	 *            User Name of client.
	 * @param hostname
	 *            Host Name of client.
	 * @param servername
	 *            Server Name of client.
	 * @param realName
	 *            Real Name of client.
	 */
	/*public Client(Connection connection, String nickname, String username, String hostname, String servername, String realName) {
		this.connection = connection;
		this.nickname = nickname;
		this.username = username;
		this.hostname = hostname;
		this.servername = servername;
		this.realName = realName;

	}*/

	public enum ClientMode {
		INVISIBLE("i"), SERVER_NOTICES("s"), WALLOPS("w"), OPERATOR("o");

		private String symbol;

		ClientMode(String letter) {
			this.symbol = letter;
		}

		public String getSymbol() {
			return symbol;
		}
	}

	/**
	 * Gets mode of user.
	 * 
	 * @param mode
	 * @return True if Mode is set to user False if not.
	 */
	public boolean getMode(ClientMode mode) {
		return modes.get(mode);
	}

	/**
	 * Sets mode to add and send the change to the user.
	 * 
	 * @param mode
	 *            Client mode to be set.
	 * @param add
	 *            What to set mode to.
	 * @param sender
	 *            Who requested the mode change.
	 * @throws IOException
	 */
	public void setMode(ClientMode mode, boolean add, Client sender) throws IOException {
		connection.send(Reply.RPL_UMODEIS, this, sender.getAbsoluteName() + " sets mode " + (add ? "+" : "-") + mode.getSymbol() + " on " + getNickname());

		modes.put(mode, add);
	}

	/**
	 * Returns the Client's nickname.
	 * 
	 * @return The Client's nickname.
	 */
	public String getNickname() {
		return nickname;
	}

	/**
	 * Returns the Client's real name.
	 * 
	 * @return The Client's real name.
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * Returns the Client's username.
	 * 
	 * @return The Client's username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the Client's nickname.
	 * 
	 * @param nickname
	 *            The Clients new nickname.
	 */
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	/**
	 * Sets the Client's real name.
	 * 
	 * @param realName
	 *            The Client's new real name.
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * Sets the Client's user name.
	 * 
	 * @param username
	 *            The Client's new user name.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Returns the Connection the Client is connected through.
	 * 
	 * @return The Connection the Client is connected through.
	 */
	public ClientConnection getConnection() {
		return connection;
	}
	
	public void setConnection(ClientConnection connection) {
		this.connection = connection;
	}

	/**
	 * Returns the hostname.
	 * 
	 * @return The hostname.
	 */
	public String getHostname() {
		return hostname;
	}

	/**
	 * Sets the hostname
	 * 
	 * @param hostname
	 *            The Client's new hostname.
	 */
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	/**
	 * Return the server name.
	 * 
	 * @return The server name.
	 */
	public String getServername() {
		return servername;
	}

	/**
	 * Sets the server name.
	 * 
	 * @param servername
	 *            The Client's new server name.
	 */
	public void setServername(String servername) {
		this.servername = servername;
	}

	/**
	 * Returns the list of channels the Client has joined.
	 * 
	 * @return The list of channels the Client has joined.
	 */
	public ArrayList<Channel> getChannels() {
		return channels;
	}

	/**
	 * Adds a Channels.
	 * 
	 * @param channel
	 *            Channels to add.
	 */
	public void addChannel(Channel channel) {
		channels.add(channel);
	}

	/**
	 * Removes a Channel.
	 * 
	 * @param channel
	 *            Channels to remove.
	 */
	public void removeChannel(Channel channel) {
		channels.remove(channel);
	}

	/**
	 * Checks if the Client has a ClientMode.
	 * 
	 * @param mode
	 *            Mode to check.
	 * @return True if Client has mode. False if not.
	 */
	public boolean hasMode(ClientMode mode) {
		if (!modes.containsKey(mode)) return false;
		return modes.get(mode);
	}

	/**
	 * Checks if Client is a Server Op.
	 * 
	 * @return If Client is a Server Op.
	 */
	public boolean isServerOP() {
		if (!modes.containsKey(ClientMode.OPERATOR)) return false;
		return modes.get(ClientMode.OPERATOR);
	}

	/**
	 * Returns the last time the user has sent a message. In Unix time.
	 * 
	 * @return The last time the user has sent a message. In Unix time.
	 */
	public long getLastCheckin() {
		return lastCheckin;
	}

	/**
	 * Sets the last time a user has sent a message. In Unix time.
	 * 
	 * @param lastCheckin
	 *            The last time a user has sent a message. In Unix time.
	 */
	public void setLastCheckin(long lastCheckin) {
		this.lastCheckin = lastCheckin;
	}

	/**
	 * Returns the hop count.
	 * 
	 * @return The hop count.
	 */
	public int getHopCount() {
		return 0;
	}

	/**
	 * Returns Nickname!Username@Hostname
	 * 
	 * @return Nickname!Username@Hostname
	 */
	public String getAbsoluteName() {
		return this.getNickname() + "!" + this.getUsername() + "@" + this.getHostname();
	}

	/**
	 * Sets away message. If away message is "" then the user is not away.
	 * 
	 * @param message
	 *            The away message.
	 */
	public void setAwayMessage(String message) {
		this.awayMessage = message;
	}

	/**
	 * Returns away message. If away message is "" then the user is not away.
	 * 
	 * @return Away message.
	 */
	public String getAwayMessage() {
		return awayMessage;
	}

}
