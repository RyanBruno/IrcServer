package com.rbruno.irc.command.client;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.Event;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.NewRequestEvent;
import com.rbruno.irc.net.Request;

public abstract class ClientCommand implements Listener {

    private String name;
    private ClientCommandModule commandModule;

    public ClientCommand(String name, ClientCommandModule commandModule) {
        this.name = name;
        this.commandModule = commandModule;
    }

    @EventListener
    public void onRequest(NewRequestEvent event) {
        if (event.getRequest().getCommand().toUpperCase().equals(name)) {
            Client client = commandModule.getClient(event.getRequest().getSocketChannel());

            if (client != null)
                commandModule.getEventDispacher().dispach(execute(event.getRequest(), client));
        }
    }

    public abstract Event execute(Request request, Client client);

    public ClientCommandModule getCommandModule() {
        return commandModule;
    }
}
