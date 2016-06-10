package com.rbruno.irc.templates;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.rbruno.irc.reply.Reply;

public class Channel {

	private String name;
	private String password;
	private ArrayList<Client> clients = new ArrayList<Client>();
	private ArrayList<Client> ops = new ArrayList<Client>();
	private HashMap<ChannelMode, Boolean> modes = new HashMap<ChannelMode, Boolean>();
	private ArrayList<Client> voiceList = new ArrayList<Client>();
	private ArrayList<Client> invitedUsers = new ArrayList<Client>();
	private int userLimit = 100;
	private String topic = "";

	public Channel(String name, String password) {
		this.name = name;
		this.password = password;
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

	public void setMode(ChannelMode mode, boolean add, Client sender) throws IOException {
		send(Reply.RPL_CHANNELMODEIS, sender + " sets mode " + (add ? "+" : "-") + mode.getSymbol() + " on " + name);
		modes.put(mode, add);
	}

	private void send(Reply reply, String message) throws IOException {
		for (Client current : clients) {
			current.getConnection().send(reply, current.getNickname(), message);
		}
	}

	public void sendMessage(Client sender, String message) throws IOException {
		for (Client current : clients)
			current.getConnection().send(current.getNickname(), "PRIVMSG", sender.getNickname() + " :" + message);
	}

	public String getName() {
		return name;
	}

	public void addClient(Client client) throws IOException {
		clients.add(client);
		client.getConnection().send(Reply.RPL_TOPIC, client, this.getName() + " :" + this.getTopic());
		String message = this.getName() + " :";
		ArrayList<Client> clients = this.getClients();
		for (Client current : clients) {
			if (this.checkOP(client) || current.isServerOP()) {
				message = message + "+" + current.getNickname() + " ";
			} else {
				message = message + "@" + current.getNickname() + " ";
			}
		}
		client.getConnection().send(Reply.RPL_NAMREPLY, client, message);
	}

	public void removeClient(Client client) {
		clients.remove(client);
	}

	public ArrayList<Client> getClients() {
		return clients;
	}

	public boolean checkPassword(String password) {
		return this.password == password;
	}

	public void setPassword(String password) {
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

	public String getTopic() {
		if (topic.equals("") || topic == null)
			return "Default Topic";
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void inviteUser(Client client) {
		this.invitedUsers.add(client);
	}

	public void unInviteUser(Client client) {
		this.invitedUsers.remove(client);
	}

	public boolean isUserInvited(Client client) {
		return invitedUsers.contains(client);
	}

	public boolean isUserOnChannel(Client client) {
		return clients.contains(client);
	}

}
