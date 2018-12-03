package com.rbruno.irc.events;

import com.rbruno.irc.events.EventDispacher.EventType;

public class ServerOpenEvent extends Event {

    @Override
    protected EventType getType() {
        return EventType.SERVER_OPEN;
    }

}
