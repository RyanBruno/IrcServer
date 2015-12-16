package com.rbruno.irc;

import java.util.ArrayList;

import com.rbruno.irc.net.AdjacentServer;

public class ServerManager {
	ArrayList<AdjacentServer> servers = new ArrayList<AdjacentServer>();

	public ServerManager() {
	}
	
	public AdjacentServer getServer(String name) {
		for (AdjacentServer server : servers) {
			if (server.getName().equals(name)) return server;
		}
		return null;
	}

	public void removeServer(AdjacentServer server) {
		servers.remove(server);
	}

	public void removeServer(String name) {
		for (AdjacentServer server : servers) {
			if (server.getName().equals(name)) removeServer(server);
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
	
	public AdjacentServer[] getAdjacentServers() {
		ArrayList<AdjacentServer> list = new ArrayList<AdjacentServer>();
		for (AdjacentServer current : servers) {
			if (current.getHopcount() == 0) list.add(current);
		}
		AdjacentServer[] array = new AdjacentServer[list.size()];
		for (int i = 0; i < list.size() ; i++) {
			array[i] = list.get(i);
		}
		return array;
		
	}

}
