package com.rbruno.irc.command;

import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.NewCommandEvent;

public class ClientCheckIn extends EventListener {

    @Override
    public void onNewCommand(NewCommandEvent event) {
        if (event.getClient().isPresent())
            event.getClient().get().setLastCheckIn(System.currentTimeMillis());
    }

}
