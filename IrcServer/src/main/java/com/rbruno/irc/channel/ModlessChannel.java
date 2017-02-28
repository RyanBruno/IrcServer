package com.rbruno.irc.channel;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;

public class ModlessChannel extends Channel {

	public ModlessChannel(String name, String password, Server server) {
		super(name, password, server);
	}
	
	@Override
	public void addOP(Client client) {
	}

}
