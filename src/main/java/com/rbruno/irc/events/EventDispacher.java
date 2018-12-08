package com.rbruno.irc.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EventDispacher {

    private List<Listener> listeners;

    public enum EventType {
        CONFIG_CHANGED,
        NEW_REQUEST, SEND_DATA,
        NICK_SET, CLIENT_CHANGED,
        CHANNEL_CHANGED,
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
                if (annotation != null && method.getParameterTypes()[0] == event.getClass()) {
                    try {
                        method.invoke(listener, event);
                    } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
