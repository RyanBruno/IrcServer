package com.rbruno.irc.events;

public abstract class Module {

    private EventDispacher eventDispacher;

    public Module(EventDispacher eventDispacher) {
        this.eventDispacher = eventDispacher;
        registerEventListeners();
    }
    
    public abstract void registerEventListeners();

    public EventDispacher getEventDispacher() {
        return eventDispacher;
    }
}
