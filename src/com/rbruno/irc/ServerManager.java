package com.rbruno.irc;

import java.util.ArrayList;

import com.rbruno.irc.net.AdjacentServer;

public class ServerManager {
	ArrayList<AdjacentServer> servers = new ArrayList<AdjacentServer>();

	public ServerManager() {
	}

	public void addClient(AdjacentServer server) {
		//TODO: Check if server is already registered
		servers.add(server);
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

	public void addServer(AdjacentServer adjacentServer) {
		// TODO 
		
	}

}
