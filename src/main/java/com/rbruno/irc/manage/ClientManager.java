package com.rbruno.irc.manage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.rbruno.irc.Server;
import com.rbruno.irc.reply.Reply;
import com.rbruno.irc.templates.Client;
import com.rbruno.irc.templates.Client.ClientMode;

public class ClientManager implements Runnable {

	ArrayList<Client> clients = new ArrayList<Client>();

	public ClientManager() {
		//Thread clientHandler = new Thread(this, "Client Handler");
		//clientHandler.run();
	}

	@Override
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Client client : clients)
						if (client.getConnection().isClient())
							if (System.currentTimeMillis() - client.getLastCheckin() >= 10000) {
								client.getConnection().send(Server.getServer().getConfig().getProperty("hostname"), "PING", ":" + Server.getServer().getConfig().getProperty("hostname"));
							}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}, 1000, 1000);
	}

	public void broadcastLocal(Reply reply, String args) throws IOException {
		for (Client client : clients)
			if (client.getConnection().isClient())
				client.getConnection().send(reply, client, args);
	}

	public void broadcastLocal(String prefix, String command, String args) throws IOException {
		for (Client client : clients)
			if (client.getConnection().isClient())
				client.getConnection().send(prefix, command, args);
	}

	public void addClient(Client client) {
		// TODO: Check if nick is in use
		clients.add(client);
	}

	public Client getClient(String nickname) {
		for (Client client : clients) {
			if (client.getNickname().equals(nickname))
				return client;
		}
		return null;
	}

	public void removeClient(Client client) {
		clients.remove(client);
	}

	public void removeClient(String nickname) {
		for (Client client : clients) {
			if (client.getNickname().equals(nickname))
				removeClient(client);
		}
	}

	public boolean isNick(String nickname) {
		for (Client client : clients) {
			if (client.getNickname().equals(nickname))
				return true;
		}
		return false;
	}

	public int getUserCount() {
		int users = 0;
		for (Client current : clients)
			if (!current.hasMode(ClientMode.INVISIBLE))
				users++;
		return users;
	}

	public int getInvisibleUserCount() {
		int users = 0;
		for (Client current : clients)
			if (current.hasMode(ClientMode.INVISIBLE))
				users++;
		return users;
	}

	public int getOps() {
		int ops = 0;
		for (Client current : clients)
			if (current.isServerOP())
				ops++;
		return ops;
	}
}
