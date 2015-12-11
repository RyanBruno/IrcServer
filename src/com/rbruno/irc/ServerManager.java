package com.rbruno.irc;

import java.util.ArrayList;

import com.rbruno.irc.net.AdjacentServer;

public class ServerManager {
	ArrayList<AdjacentServer> servers = new ArrayList<AdjacentServer>();

	public ServerManager() {
	}
	
	public AdjacentServer getClient(String name) {
		for (AdjacentServer server : servers) {
			if (server.getName().equals(name)) return server;
		}
		return null;
	}

	public void removeClient(AdjacentServer server) {
		servers.remove(server);
	}

	public void removeClient(String name) {
		for (AdjacentServer server : servers) {
			if (server.getName().equals(name)) removeClient(server);
		}
	}

	public void addServer(AdjacentServer server) {
		servers.add(server);

	}

	public boolean checkServer(String serverName) {
		for (AdjacentServer current: servers) {
			if (current.getName().equals(serverName)) return true;
		}
		return false;
	}

}
