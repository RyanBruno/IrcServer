package com.rbruno.irc.events;

import java.nio.channels.SocketChannel;

import com.rbruno.irc.events.EventDispacher.EventType;

public class NickSetEvent extends Event {
    
    private SocketChannel socketChannel;
    private String nickname;

    public NickSetEvent(SocketChannel socketChannel, String nickname) {
        this.socketChannel = socketChannel;
        this.nickname = nickname;
    }

    @Override
    protected EventType getType() {
        return EventType.NICK_SET;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public String getNickname() {
        return nickname;
    }

}
