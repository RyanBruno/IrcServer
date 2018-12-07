package com.rbruno.irc.events;

import java.util.ArrayList;
import java.util.List;

public class EventDispacher {

    private List<EventListener> listeners;

    public enum EventType {
        CONFIG_CHANGED,
        NEW_REQUEST, SEND_DATA,
        NICK_SET, CLIENT_CHANGED,
        CHANNEL_CHANGED,
    }

    public EventDispacher() {
        listeners = new ArrayList<EventListener>();
    }

    public void registerListener(EventListener eventListener) {
        listeners.add(eventListener);
    }

    public void dispach(Event event) {
        for (EventListener listener : listeners) {   	
            switch (event.getType()) {

            }
            // TODO if event.isCanceled();
        }
    }

}
