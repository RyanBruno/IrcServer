package com.rbruno.irc.command.client;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.events.ClientChangedEvent;
import com.rbruno.irc.events.ConfigChangedEvent;
import com.rbruno.irc.events.EventDispacher;
import com.rbruno.irc.events.EventListener;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.events.Module;

public class ClientCommandModule extends Module implements Listener {
	
	private Map<SocketChannel, Client> clientMap;
	
	private String hostname;

    public ClientCommandModule(EventDispacher eventDispacher) {
        super(eventDispacher);
        clientMap = new HashMap<>();
    }

    @Override
    public void registerEventListeners() {
        getEventDispacher().registerListener(this);

        getEventDispacher().registerListener(new Nick(this));
        getEventDispacher().registerListener(new Oper(this));
        getEventDispacher().registerListener(new Quit(this));
        // commands.add(new Squit());
        getEventDispacher().registerListener(new Join(this));
        getEventDispacher().registerListener(new Part(this));
        getEventDispacher().registerListener(new Mode(this));
        getEventDispacher().registerListener(new Topic(this));
        getEventDispacher().registerListener(new Names(this));
        getEventDispacher().registerListener(new List(this));
        getEventDispacher().registerListener(new Invite(this));
        getEventDispacher().registerListener(new Kick(this));
        getEventDispacher().registerListener(new Version(this));
        // commands.add(new Stats());
        // commands.add(new Links());
        getEventDispacher().registerListener(new Time(this));
        // commands.add(new Connect());
        // commands.add(new Trace());
        getEventDispacher().registerListener(new Admin(this));
        getEventDispacher().registerListener(new Info(this));
        getEventDispacher().registerListener(new Privmsg(this));
        getEventDispacher().registerListener(new Notice(this));
        getEventDispacher().registerListener(new Who(this));
        getEventDispacher().registerListener(new Whois(this));
        // commands.add(new Whowas());
        // commands.add(new Kill());
        getEventDispacher().registerListener(new Ping(this));
        //getEventDispacher().registerListener(new Pong(this));
        // commands.add(new Error());
        // Optional Commands
        getEventDispacher().registerListener(new Away(this));
    }

    public Client getClient(SocketChannel socketChannel) {
        return clientMap.get(socketChannel);
    }
    
    @EventListener
    public void onClientUpdated(ClientChangedEvent event) {
    	clientMap.put(event.getClient().getSocketChannel(), event.getClient());
    }
    
    @EventListener
    public void onConfigChange(ConfigChangedEvent event) {
    	hostname = event.getConfig().getHostname();
    }

	public String getHostname() {
		return hostname;
	}

}
