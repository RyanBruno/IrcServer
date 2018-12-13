package com.rbruno.irc.events;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventDispacher.EventType;

public class ClientChangedEvent extends Event {

    private Client client;
    private ClientChangeType changeType;

    public ClientChangedEvent(Client client, ClientChangeType changeType) {
        this.client = client;
        this.changeType = changeType;
    }

    @Override
    protected EventType getType() {
        return EventType.CHANNEL_CHANGED;
    }
    

    public enum ClientChangeType {
        CLIENT_REGISTERED, CLIENT_DISCONNECT,
        NICK_CHANGED, UNAWAY, AWAY_SET
    }

    public Client getClient() {
        return client;
    }
    
    public ClientChangeType getChangeType() {
        return changeType;
    }

    @Override
    public String toString() {
        return getType().name() + " " + client.getAbsoluteName() + " " + changeType.name();
    }

}
