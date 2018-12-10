package com.rbruno.irc.events;

import org.apache.commons.lang3.ArrayUtils;

import com.rbruno.irc.events.EventDispacher.EventType;
import com.rbruno.irc.net.Request;

public class NewRequestEvent extends Event {

    private Request request;

    public NewRequestEvent(Request request) {
        this.request = request;
    }

    @Override
    protected EventType getType() {
        return EventType.NEW_REQUEST;
    }

    public Request getRequest() {
        return request;
    }

    @Override
    public String toString() {
        return getType().name() + " " + request.getPrefix() + " " + request.getCommand() + " " + ArrayUtils.toString(request.getArgs());
    }
}
