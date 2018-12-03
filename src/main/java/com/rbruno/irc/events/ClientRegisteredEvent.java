package com.rbruno.irc.events;

import java.nio.channels.SocketChannel;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.EventDispacher.EventType;

public class ClientRegisteredEvent extends Event {
    
    private SocketChannel socketChannel;
    private Client client;

    public ClientRegisteredEvent(SocketChannel socketChannel, Client client) {
        this.socketChannel = socketChannel;
        this.client = client;
    }

    @Override
    protected EventType getType() {
        return EventType.CLIENT_REGISTERED;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
    
    public Client getClient() {
        return client;
    }

}
