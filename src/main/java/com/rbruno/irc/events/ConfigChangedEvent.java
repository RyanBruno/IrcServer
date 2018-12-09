package com.rbruno.irc.events;

import com.rbruno.irc.config.Config;
import com.rbruno.irc.events.EventDispacher.EventType;

public class ConfigChangedEvent extends Event {
    
    private Config config;

    public ConfigChangedEvent(Config config) {
        this.config = config;
    }

    @Override
    protected EventType getType() {
        return EventType.CONFIG_CHANGED;
    }

    public Config getConfig() {
        return config;
    }

    @Override
    public String toString() {
        return getType().name();
    }
}
