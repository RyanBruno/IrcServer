package com.rbruno.irc.events;

import java.util.ArrayList;
import java.util.List;

public class EventDispacher {

    private List<EventListener> listeners;

    public enum EventType {
        SERVER_OPEN, NEW_CONNECTION, NEW_LINE, NEW_REQUEST, 
        NEW_REG_COMMAND, NEW_CLIENT_COMMAND,
        CLIENT_REGISTERED, CLINET_DISCONNECT,
        NICK_SET, NICK_CHANGED
        
        
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
            case SERVER_OPEN:
                listener.onServerOpen((ServerOpenEvent) event);
                break;
            case NEW_CONNECTION:
                listener.onNewConnection((NewConnectionEvent) event);
                break;
            case NEW_LINE:
                listener.onNewLine((NewLineEvent) event);
                break;
            case NEW_REQUEST:
                listener.onNewRequest((NewRequestEvent) event);
                break;
            case NEW_COMMAND:
                listener.onNewCommand((NewCommandEvent) event);
                break;
            }
            // TODO if event.isCanceled();
        }
    }

}
