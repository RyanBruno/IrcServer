package com.rbruno.irc.events;

import com.rbruno.irc.events.EventDispacher.EventType;

import io.netty.channel.Channel;

public class NickSetEvent extends Event {
    
    private Channel channel;
    private String nickname;

    public NickSetEvent(Channel channel, String nickname) {
        this.channel = channel;
        this.nickname = nickname;
    }

    @Override
    protected EventType getType() {
        return EventType.NICK_SET;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getNickname() {
        return nickname;
    }
    

    @Override
    public String toString() {
        return getType().name() + " " + nickname;
    }

}
