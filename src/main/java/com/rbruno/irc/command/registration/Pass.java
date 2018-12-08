package com.rbruno.irc.command.registration;

import com.rbruno.irc.events.Listener;
import com.rbruno.irc.net.Request;

public class Pass extends RegistrationCommand implements Listener {

    public Pass(RegCommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request) {
        // TODO
    }
}
