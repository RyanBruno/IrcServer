package com.rbruno.irc;

import java.io.IOException;
import java.util.ArrayList;

import com.rbruno.irc.net.Client;

public class ClientManager {

	ArrayList<Client> clients = new ArrayList<Client>();

	public ClientManager() {
	}

	public void broadcastLocal(Reply reply, String args) throws IOException {
		for (Client client : clients) {
			if (client.getConnection().isClient())
				client.getConnection().send(reply, client, args);
		}
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
}
