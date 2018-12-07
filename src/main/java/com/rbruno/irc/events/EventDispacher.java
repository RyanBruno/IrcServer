package com.rbruno.irc.events;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventDispacher {

    private List<Listener> listeners;

    public enum EventType {
        SERVER_OPEN, NEW_CONNECTION, NEW_REQUEST, SEND_DATA,
        CONFIG_CHANGED,
        CLIENT_REGISTERED, CLIENT_DISCONNECT,
        NICK_SET, NICK_CHANGED,
        
    }

    public EventDispacher() {
        listeners = new ArrayList<Listener>();
    }

    public void registerListener(Listener listener) {
        listeners.add(listener);
    }

    public void dispach(Event event) {
        for (Listener listener : listeners) {   	
            for (Method method : listener.getClass().getMethods()) {
                EventListener annotation = method.getAnnotation(EventListener.class);
                if (annotation != null && method.getParameterTypes()[0].equals(ClientRegisteredEvent.class)) {
                    method.invoke(listener, () event);
                }
            }
        }
    }

}
