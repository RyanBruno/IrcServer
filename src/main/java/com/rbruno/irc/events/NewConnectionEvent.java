package com.rbruno.irc.events;

import java.net.Socket;

import com.rbruno.irc.events.EventDispacher.EventType;

public class NewConnectionEvent extends Event {
    
    private Socket socket;

    public NewConnectionEvent(Socket socket) {
        this.socket = socket;
    }

    @Override
    protected EventType getType() {
        return EventType.NEW_CONNECTION;
    }

    public Socket getSocket() {
        return socket;
    }

}
