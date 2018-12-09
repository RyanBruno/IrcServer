package com.rbruno.irc.command.client;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.ClientChangedEvent;
import com.rbruno.irc.events.ClientChangedEvent.ClientChangeType;
import com.rbruno.irc.events.Event;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.net.Request;

public class Nick extends ClientCommand implements Listener {

    public Nick(ClientCommandModule commandModule) {
        super("NICK", commandModule);
    }

    @Override
    public Event execute(Request request, Client client) {
        if (request.getArgs().length > 0) {
            // TODO Check for Nick collisions
            // TODO Check nick is valid
            return new ClientChangedEvent(client.setNickname(request.getArgs()[0]), ClientChangeType.NICK_CHANGED);    
        } else {
            // TODO No Nick 
        }
    }

}
