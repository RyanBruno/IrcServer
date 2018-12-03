package com.rbruno.irc.events;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventDispacher.EventType;
import com.rbruno.irc.net.Request;

public class NewClientCommandEvent extends Event {

	private Request request;
	private Client client;

	public NewClientCommandEvent(Request request, Client client) {
		this.request = request;
		// TODO assert clinet is not null
		this.client = client;
	}

	@Override
	protected EventType getType() {
		return EventType.NEW_CLIENT_COMMAND;
	}

	public Request getRequest() {
		return request;
	}

	public Client getClient() {
		return client;
	}

}
