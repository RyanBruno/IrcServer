package com.rbruno.irc.command.client;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.Event;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.SendEventBuilder;

public class Ping extends ClientCommand {

    public Ping(ClientCommandModule commandModule) {
		super("PING", commandModule);
	}

	@Override
	public Event execute(Request request, Client client) {
		SendEventBuilder builder = new SendEventBuilder(getCommandModule().getHostname());
		builder.addMessage(":" + getCommandModule().getHostname() + " PONG " + client.getAbsoluteName());
		return builder.getEvent();
	}

}
