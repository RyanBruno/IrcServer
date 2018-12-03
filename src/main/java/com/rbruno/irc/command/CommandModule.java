package com.rbruno.irc.command;

import java.nio.channels.SocketChannel;
import java.util.HashMap;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.ClientRegisteredEvent;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Module;
import com.rbruno.irc.events.NickSetEvent;

public class CommandModule extends Module {
    
    private HashMap<SocketChannel, Client> clients;
    private HashMap<SocketChannel, String> nicks;


    public CommandModule(EventDispacher eventDispacher) {
        super(eventDispacher);
        clients = new HashMap<SocketChannel, Client>();
        nicks = new HashMap<SocketChannel, String>();
    }

    @Override
    public void registerEventListeners() {
        getEventDispacher().registerListener(new ClientCheckIn());

        getEventDispacher().registerListener(new RegistrationCommandInvoker(this));
        getEventDispacher().registerListener(new ClientCommandInvoker(this));
        
        getEventDispacher().registerListener(new EventListener() {
            @Override
            public void onClientRegistered(ClientRegisteredEvent event) {
                clients.put(event.getSocketChannel(), event.getClient());
            }
            
            @Override
            public void onNickSet(NickSetEvent event) {
                nicks.put(event.getSocketChannel(), event.getNickname());
            }
        });
    }

    public Client getClient(SocketChannel socketConnection) {
        return clients.get(socketConnection);
    }

    public String getNickname(SocketChannel socketConnection) {
        return nicks.get(socketConnection);
    }

}
