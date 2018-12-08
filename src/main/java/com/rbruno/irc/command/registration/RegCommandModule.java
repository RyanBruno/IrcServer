package com.rbruno.irc.command.registration;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.Module;
import com.rbruno.irc.events.NickSetEvent;

public class RegCommandModule extends Module implements Listener {
    
    private HashMap<SocketChannel, String> nicks;

    public RegCommandModule(EventDispacher eventDispacher) {
        super(eventDispacher);
        nicks = new HashMap<SocketChannel, String>();
    }

    @Override
    public void registerEventListeners() {
        getEventDispacher().registerListener(this);

        RegistrationCommandInvoker.registerEventListeners(this);        
    }
    
    @EventListener
    public void onNickSet(NickSetEvent event) {
        nicks.put(event.getSocketChannel(), event.getNickname());
    }

    public String getNickname(SocketChannel socketChannel) {
        return nicks.get(socketChannel);
    }

}
