package com.rbruno.irc.events;

import com.rbruno.irc.events.EventDispacher.EventType;

public abstract class Event {

    protected abstract EventType getType();
}
