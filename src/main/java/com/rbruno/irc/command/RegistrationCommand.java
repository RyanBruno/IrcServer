package com.rbruno.irc.command;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.net.Request;

public abstract class RegistrationCommand {
	
	private EventDispacher eventDispacher;
	
	protected RegistrationCommand(EventDispacher eventDispacher) {
		this.eventDispacher = eventDispacher;
	}
	
    public abstract void execute(Request request);

	public EventDispacher getEventDispacher() {
		return eventDispacher;
	}
}
