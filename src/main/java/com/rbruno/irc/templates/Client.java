package com.rbruno.irc.templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.rbruno.irc.net.Connection;
import com.rbruno.irc.reply.Reply;

public class Client {

	private Connection connection;
	private String nickname;
	private String username;
	private String hostname;
	private String servername;
	private String realName;

	private long lastCheckin;

	private ArrayList<Channel> channels = new ArrayList<Channel>();
	private HashMap<ClientMode, Boolean> modes = new HashMap<ClientMode, Boolean>();

	public Client(Connection connection, String nickname) {
		this.connection = connection;
		this.nickname = nickname;
	}

	public Client(Connection connection, String nickname, String username, String hostname, String servername, String realName) {
		this.connection = connection;
		this.nickname = nickname;
		this.username = username;
		this.hostname = hostname;
		this.servername = servername;
		this.realName = realName;

	}

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

	public boolean getMode(ClientMode mode) {
		return modes.get(mode);
	}

	public void setMode(ClientMode mode, boolean add, Client sender) throws IOException {
		connection.send(Reply.RPL_UMODEIS, this, sender.getAbsoluteName() + " sets mode " + (add ? "+" : "-") + mode.getSymbol() + " on " + getNickname());

		modes.put(mode, add);
	}

	public String getNickname() {
		return nickname;
	}

	public String getRealName() {
		return realName;
	}

	public String getUsername() {
		return username;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Connection getConnection() {
		return connection;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public ArrayList<Channel> getChannels() {
		return channels;
	}

	public void addChannel(Channel channel) {
		channels.add(channel);
	}

	public void removeChannel(Channel channel) {
		channels.remove(channel);
	}

	public boolean hasMode(ClientMode mode) {
		if (!modes.containsKey(mode))
			return false;
		return modes.get(mode);
	}

	public boolean isServerOP() {
		if (!modes.containsKey(ClientMode.OPERATOR))
			return false;
		return modes.get(ClientMode.OPERATOR);
	}

	public long getLastCheckin() {
		return lastCheckin;
	}

	public void setLastCheckin(long lastCheckin) {
		this.lastCheckin = lastCheckin;
	}

	public int getHopCount() {
		return 0;
	}
	
	public String getAbsoluteName() {
		return this.getNickname() + "!" + this.getUsername() + "@" + this.getHostname();
	}

}
