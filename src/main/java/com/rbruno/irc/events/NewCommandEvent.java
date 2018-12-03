package com.rbruno.irc.events;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventDispacher.EventType;
import com.rbruno.irc.net.Request;

public class NewCommandEvent extends Event {

    private Request request;
    private Optional<Client> client;

    public NewCommandEvent(Request request, Optional<Client> client) {
        this.request = request;
        this.client = client;
    }

    @Override
    protected EventType getType() {
        return EventType.NEW_COMMAND;
    }

    public Request getRequest() {
        return request;
    }

    public Optional<Client> getClient() {
        return client;
    }

}
