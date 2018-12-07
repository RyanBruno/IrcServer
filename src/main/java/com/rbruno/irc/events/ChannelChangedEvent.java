package com.rbruno.irc.events;

import com.rbruno.irc.channel.Channel;
import com.rbruno.irc.events.EventDispacher.EventType;

public class ChannelChangedEvent extends Event {

    private Channel channel;
    private ChannelChangeType changeType;

    public ChannelChangedEvent(Channel channel, ChannelChangeType changeType) {
        this.channel = channel;
        this.changeType = changeType;
    }

    @Override
    protected EventType getType() {
        return EventType.CHANNEL_CHANGED;
    }
    

    public enum ChannelChangeType {
        CHANNEL_CREATED, CHANNEL_DISTROYED,
        CHANNEL_TOPIC_SET,
        CLIENT_JOIN_CHANNEL, CLIENT_PART_CHANNEL,
    }

    public Channel getChannel() {
        return channel;
    }
    
    public ChannelChangeType getChangeType() {
        return changeType;
    }

}
