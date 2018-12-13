package com.rbruno.irc.command;

import com.rbruno.irc.command.registration.RegCommandModule;
import com.rbruno.irc.events.Event;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.net.Request;

public class Pass extends RegistrationCommand implements Listener {

    public Pass(RegCommandModule commandModule) {
        super("PASS", commandModule);
    }

    @Override
    public Event execute(Request request) {
        // TODO
        return null;
    }
}
