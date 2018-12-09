package com.rbruno.irc.command.client;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.Event;
import com.rbruno.irc.net.Request;

public class Pong extends ClientCommand {

	public Pong(ClientCommandModule commandModule) {
		super("PONG", commandModule);
	}

	@Override
	public Event execute(Request request, Client client) {
		return null;
	}

}
