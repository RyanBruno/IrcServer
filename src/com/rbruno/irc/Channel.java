package com.rbruno.irc;

import java.util.ArrayList;

import com.rbruno.irc.net.Client;

public class Channel {
	
	private String name;
	private String password;
	private ArrayList<Client> clients = new ArrayList<Client>();

	public Channel() {
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


}
