package com.rbruno.irc.command.registration;

import com.rbruno.irc.command.RegistrationCommand;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.NickSetEvent;
import com.rbruno.irc.net.Request;

public class Nick extends RegistrationCommand {

    protected Nick(EventDispacher eventDispacher) {
		super(eventDispacher);
	}

	@Override
    public void execute(Request request) {
    	if (request.getArgs().length > 0) {
    		// TODO Nick in use
    		getEventDispacher().dispach(new NickSetEvent(request.getConnection(), request.getArgs()[0]));
    	} else {
    		// TODO no nick given
    	}
    }

}
