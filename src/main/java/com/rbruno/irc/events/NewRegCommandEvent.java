package com.rbruno.irc.events;

import com.rbruno.irc.events.EventDispacher.EventType;
import com.rbruno.irc.net.Request;

public class NewRegCommandEvent extends Event {

    private Request request;

    public NewRegCommandEvent(Request request) {
        this.request = request;
    }

    @Override
    protected EventType getType() {
        return EventType.NEW_REG_COMMAND;
    }

    public Request getRequest() {
        return request;
    }

}
