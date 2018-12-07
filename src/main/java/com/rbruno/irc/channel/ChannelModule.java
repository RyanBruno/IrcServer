package com.rbruno.irc.channel;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.Module;

public class ChannelModule extends Module {

    public ChannelModule(EventDispacher eventDispacher) {
        super(eventDispacher);
    }

    @Override
    public void registerEventListeners() {
        
    }

}
