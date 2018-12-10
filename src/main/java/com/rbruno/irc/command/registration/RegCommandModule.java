package com.rbruno.irc.command.registration;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

import com.rbruno.irc.events.ClientChangedEvent;
import com.rbruno.irc.events.ClientChangedEvent.ClientChangeType;
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
        getEventDispacher().registerListener(new Pass(this));
        getEventDispacher().registerListener(new Nick(this));
        getEventDispacher().registerListener(new User(this));
    }

    @EventListener
    public void onNickSet(NickSetEvent event) {
        nicks.put(event.getSocketChannel(), event.getNickname());
    }

    @EventListener
    public void onClientChanged(ClientChangedEvent event) {
        if (event.getChangeType() == ClientChangeType.CLIENT_REGISTERED)
            nicks.remove(event.getClient().getSocketChannel());
    }

    public String getNickname(SocketChannel socketChannel) {
        return nicks.get(socketChannel);
    }

    public boolean isNickInUse(String string) {
        Iterator<String> it = nicks.values().iterator();
        while (it.hasNext()) {
            if (it.next().equals(string))
                return true;
        }
        return false;
    }

}
