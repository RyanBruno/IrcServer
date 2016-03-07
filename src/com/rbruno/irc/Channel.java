package com.rbruno.irc;

import java.util.ArrayList;
import java.util.HashMap;

import com.rbruno.irc.net.Client;

public class Channel {

	private String name;
	private String password;
	private ArrayList<Client> clients = new ArrayList<Client>();
	private ArrayList<Client> ops = new ArrayList<Client>();
	private HashMap<ChannelMode, Boolean> modes = new HashMap<ChannelMode, Boolean>();
	private ArrayList<Client> voiceList = new ArrayList<Client>();
	private int userLimit = 100;

	public Channel() {
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

	public boolean getMode(ChannelMode mode) {
		return modes.get(mode);
	}

	public void setMode(ChannelMode mode, boolean add, Client sender) {
		send(Reply.RPL_CHANNELMODEIS, this, sender + " sets mode " + (add ? "+" : "-") + mode.getSymbol() + " on " + name);
		modes.put(mode, add);
	}

	private void send(Reply rplChannelmodeis, Channel channel, String string) {
		// TODO Send to all clients

	}

	public String getName() {
		return name;
	}

	public void addClient(Client client) {
		clients.add(client);
	}

	public void removeClient(Client client) {
		clients.remove(client);
	}

	public boolean checkPassword(String password) {
		return this.password == password;
	}
	
	public void setPassword(String password){
		this.password = password;
	}

	public boolean checkOP(Client client) {
		return ops.contains(client);
	}

	public void addOP(Client client) {
		ops.add(client);
	}

	public void setUserLimit(int limit) {
		this.userLimit = limit;
	}

	public int getUserLimit() {
		return userLimit;
	}

	public int getCurrentNumberOfUsers() {
		return clients.size();
	}

	public void giveVoice(Client voicee) {
		voiceList.add(voicee);
	}

	public void takeVoice(Client voicee) {
		voiceList.remove(voicee);
	}

	public boolean hasVoice(Client voicee) {
		return voiceList.contains(voicee);
	}

}
