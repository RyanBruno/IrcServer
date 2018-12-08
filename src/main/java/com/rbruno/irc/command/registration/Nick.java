package com.rbruno.irc.command.registration;

import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.NickSetEvent;
import com.rbruno.irc.net.Request;

public class Nick extends RegistrationCommand implements Listener {

    public Nick(RegCommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request) {
        if (request.getArgs().length > 0) {
            // TODO Nick in use
            getCommandModule().getEventDispacher().dispach(new NickSetEvent(request.getSocketChannel(), request.getArgs()[0]));
        } else {
            // TODO no nick given
        }
    }

}
