package com.rbruno.irc.command;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.NewRequestEvent;

public class ClientCheckIn implements Listener {

    private CommandModule commandModule;

    @EventListener
    public void onNewRequest(NewRequestEvent event) {
        Client client = commandModule.getClient(event.getRequest().getSocketChannel());
        if (client != null)
            client.setLastCheckIn(System.currentTimeMillis());
    }

}
