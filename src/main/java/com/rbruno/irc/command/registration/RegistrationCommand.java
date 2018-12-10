package com.rbruno.irc.command.registration;

import com.rbruno.irc.events.Event;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.NewRequestEvent;
import com.rbruno.irc.net.Request;

public abstract class RegistrationCommand implements Listener {

    private String name;
    private RegCommandModule commandModule;

    public RegistrationCommand(String name, RegCommandModule commandModule) {
        this.name = name;
        this.commandModule = commandModule;
    }

    @EventListener
    public void onRequest(NewRequestEvent event) {
        if (event.getRequest().getCommand().toUpperCase().equals(name))
            commandModule.getEventDispacher().dispach(execute(event.getRequest()));
    }

    public abstract Event execute(Request request);

    public RegCommandModule getCommandModule() {
        return commandModule;
    }
}
