package com.rbruno.irc.events;

import java.util.ArrayList;
import java.util.List;

public class EventDispacher {

    private List<EventListener> listeners;

    public enum EventType {
        SERVER_OPEN, NEW_CONNECTION, NEW_REQUEST, SEND_DATA,
        CONFIG_CHANGED,
        CLIENT_REGISTERED, CLIENT_DISCONNECT,
        NICK_SET, NICK_CHANGED,
        
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
            case NEW_REQUEST:
                listener.onNewRequest((NewRequestEvent) event);
                break;
            case SEND_DATA:
                listener.onSendData((SendDataEvent) event);
                break;
            case CONFIG_CHANGED:
                listener.onConfigChanged((ConfigChangedEvent) event);
                break;
            case CLIENT_REGISTERED:
                listener.onClientRegistered((ClientRegisteredEvent) event);
                break;
            }
            // TODO if event.isCanceled();
        }
    }

}
