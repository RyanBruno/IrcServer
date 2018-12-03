package com.rbruno.irc.events;

import java.nio.channels.ServerSocketChannel;

import com.rbruno.irc.events.EventDispacher.EventType;

public class ServerOpenEvent extends Event {

    private ServerSocketChannel serverSocketChannel;

    public ServerOpenEvent(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    @Override
    protected EventType getType() {
        return EventType.SERVER_OPEN;
    }

    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

}
