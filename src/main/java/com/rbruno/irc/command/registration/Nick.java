package com.rbruno.irc.command.registration;

import com.rbruno.irc.events.Event;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.NickSetEvent;
import com.rbruno.irc.net.Request;

public class Nick extends RegistrationCommand implements Listener {

    public Nick(RegCommandModule commandModule) {
        super("NICK", commandModule);
    }

    @Override
    public Event execute(Request request) {
        if (request.getArgs().length > 0) {
            if (getCommandModule().isNickInUse(request.getArgs()[0])) {
                // TODO return nick in use
            }
            return new NickSetEvent(request.getSocketChannel(), request.getArgs()[0]);
        }
        // TODO Send nonickgiven
        return null;
    }

}
