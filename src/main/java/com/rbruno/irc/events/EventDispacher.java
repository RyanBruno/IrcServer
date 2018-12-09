package com.rbruno.irc.events;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class EventDispacher {

    private Map<Method, Listener> listeners;

    public enum EventType {
        CONFIG_CHANGED, NEW_REQUEST, SEND_DATA, NICK_SET, CLIENT_CHANGED, CHANNEL_CHANGED,
    }

    public EventDispacher() {
        listeners = new HashMap<Method, Listener>();
    }

    public void registerListener(Listener listener) {
        for (Method method : listener.getClass().getMethods()) {
            if (method.isAnnotationPresent(EventListener.class))
                listeners.put(method, listener);
        }
    }

    public void dispach(Event event) {
        Iterator<Entry<Method, Listener>> it = listeners.entrySet().iterator();

        while (it.hasNext()) {
            Entry<Method, Listener> entry = it.next();

            if (entry.getKey().getParameterTypes()[0] == event.getClass()) {
                try {
                    entry.getKey().invoke(entry.getValue(), event);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
