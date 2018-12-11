package com.rbruno.irc.command.registration;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;

import com.rbruno.irc.events.ClientChangedEvent;
import com.rbruno.irc.events.ClientChangedEvent.ClientChangeType;
import com.rbruno.irc.events.ConfigChangedEvent;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.Module;
import com.rbruno.irc.events.NickSetEvent;

import io.netty.channel.Channel;

public class RegCommandModule extends Module implements Listener {

    private HashMap<Channel, String> nicks;
    private String hostname;

    public RegCommandModule(EventDispacher eventDispacher) {
        super(eventDispacher);
        nicks = new HashMap<Channel, String>();
    }

    @Override
    public void registerEventListeners() {
        getEventDispacher().registerListener(this);
    }
    
    @EventListener
    public void onConfigChanged(ConfigChangedEvent event) {
        this.hostname = event.getConfig().getHostname();
    }

    @EventListener
    public void onNickSet(NickSetEvent event) {
        nicks.put(event.getChannel(), event.getNickname());
    }

    @EventListener
    public void onClientChanged(ClientChangedEvent event) {
        if (event.getChangeType() == ClientChangeType.CLIENT_REGISTERED)
            nicks.remove(event.getClient().getChannel());
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

    public String getHostname() {
        return hostname;
    }

}
