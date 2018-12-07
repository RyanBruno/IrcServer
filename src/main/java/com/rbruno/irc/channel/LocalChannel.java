package com.rbruno.irc.channel;

import java.security.InvalidParameterException;

import org.apache.commons.lang3.ArrayUtils;

import com.rbruno.irc.client.Client;

public class LocalChannel implements Channel {

    private String name;
    private String topic;

    private Client[] clients;

    public LocalChannel(String name, String topic, Client[] clients) {
        this.name = name;
        this.topic = topic;

        this.clients = clients;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public Channel addClient(Client client) {
        return new LocalChannel(getName(), getTopic(), ArrayUtils.add(clients, client));
    }

    @Override
    public Channel removeClient(Client client) {
        int index = ArrayUtils.indexOf(clients, client);
        
        if (index > 0) {
            throw new InvalidParameterException("Cannot remove a client that is not found!");
        }
        
        return new LocalChannel(getName(), getTopic(), ArrayUtils.remove(clients, index));
    }

    @Override
    public Channel setTopic(String topic) {
        return new LocalChannel(getName(), topic, clients);
    }

    @Override
    public boolean hasClient(Client client) {
        return ArrayUtils.contains(clients, client);
    }

}
