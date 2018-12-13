package com.rbruno.irc.events;

import com.rbruno.irc.bus.CommandBus;

public abstract class Module {

    private EventDispacher eventDispacher;
    private CommandBus bus;

    public Module(EventDispacher eventDispacher, CommandBus bus) {
        this.eventDispacher = eventDispacher;
        this.bus = bus;
        registerEventListeners();
    }
    
    public abstract void registerEventListeners();
    
    public abstract void setForwards();


    public EventDispacher getEventDispacher() {
        return eventDispacher;
    }

    public CommandBus getBus() {
        return bus;
    }
}
