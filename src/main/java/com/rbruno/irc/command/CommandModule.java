package com.rbruno.irc.command;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.Module;

public class CommandModule extends Module {

    public CommandModule(EventDispacher eventDispacher) {
        super(eventDispacher);
    }

    @Override
    public void registerEventListeners() {
        getEventDispacher().registerListener(new ClientCheckIn());

        getEventDispacher().registerListener(new RegistrationCommandInvoker());
        getEventDispacher().registerListener(new ClientCommandInvoker());
    }

}
