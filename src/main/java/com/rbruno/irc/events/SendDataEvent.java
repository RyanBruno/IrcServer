package com.rbruno.irc.events;

import java.nio.channels.SocketChannel;

import com.rbruno.irc.events.EventDispacher.EventType;

public class SendDataEvent extends Event {

    private SocketChannel socketChannel;
    private byte[] data;

    public SendDataEvent(SocketChannel socketChannel, byte[] data) {
        this.socketChannel = socketChannel;
        this.data = data;
    }

    @Override
    protected EventType getType() {
        return EventType.SEND_DATA;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public byte[] getData() {
        return data;
    }
    
    @Override
    public String toString() {
        return getType().name();
    }

}
