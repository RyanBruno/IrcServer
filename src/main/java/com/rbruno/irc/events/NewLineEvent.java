package com.rbruno.irc.events;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventDispacher.EventType;

public class NewLineEvent extends Event {
    
    private String line;
    private Optional<Client> client;

    public NewLineEvent(String line, Optional<Client> client) {
        this.line = line;
        this.client = client;
    }

    @Override
    protected EventType getType() {
        return EventType.NEW_LINE;
    }

    public String getLine() {
        return line;
    }

    public Optional<Client> getClient() {
        return client;
    }
}
