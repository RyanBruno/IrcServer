package com.rbruno.irc.net;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.Module;

public class NetworkingModule extends Module {

    public NetworkingModule(EventDispacher eventDispacher) {
        super(eventDispacher);
    }

    @Override
    public void registerEventListeners() {
        getEventDispacher().registerListener(new ConnectionListener());
    }

}
