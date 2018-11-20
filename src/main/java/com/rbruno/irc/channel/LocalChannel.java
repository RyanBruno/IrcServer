package com.rbruno.irc.channel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import com.rbruno.irc.client.Client;

public class LocalChannel implements Channel {

    private String name;
    private String topic;
    private IrcMessenger messenger;

    private ArrayList<Client> clients;
    private ArrayList<Client> ops;
    private ArrayList<Client> banned;
    private ArrayList<Client> voice;

    private ChannelMode modes;

    public LocalChannel(String name) {
        this.name = name;
        this.topic = "Default Topic";
        messenger = new IrcMessenger();

        clients = new ArrayList<Client>(5);
        ops = new ArrayList<Client>(2);
        banned = new ArrayList<Client>(2);
        voice = new ArrayList<Client>(5);

        modes = new ChannelMode();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        messenger.sendTopicToAll(this, getIterator());
        this.topic = topic;
    }

    @Override
    public void addClient(Client client) {
        messenger.sendJoinMessage(this, client);
        messenger.sendNames(this, client, getIterator());
        messenger.sendTopic(this, client);
        clients.add(client);
    }

    @Override
    public void sendToAll(String message) {
        Iterator<Client> clients = getIterator();
        while (clients.hasNext()) {
            Client client = clients.next();
            client.getConnection().send(message);
        }
    }
    
    @Override
    public void partClient(Client client, Optional<String> message) {
        messenger.clientPart(this, client, message.isPresent() ? message.get() : "Leaving");
        clients.remove(client);
    }

    @Override
    public void quitClient(Client client, Optional<String> message) {
        messenger.clientQuit(this, client, message.isPresent() ? message.get() : "Leaving");
        clients.remove(client);
    }
    

    @Override
    public void kickClient(Client client, Optional<String> message) {
        messenger.clientKick(this, client, message.isPresent() ? message.get() : "You have been kicked from the channel");
        clients.remove(client);        
    }

    @Override
    public Iterator<Client> getIterator() {
        return clients.iterator();
    }

    @Override
    public boolean isChanOp(Client client) {
        return ops.contains(client);
    }

    @Override
    public void setChanOp(Client client, boolean op) {
        if (op) {
            ops.add(client);
        } else {
            ops.remove(client);
        }
    }

    @Override
    public boolean isBanned(Client client) {
        return banned.contains(client);
    }

    @Override
    public void setBanned(Client client, boolean banned) {
        if (banned) {
            this.banned.add(client);
        } else {
            this.banned.remove(client);
        }
    }

    @Override
    public boolean hasVoice(Client client) {
        return voice.contains(client);
    }

    @Override
    public void setVoice(Client client, boolean voice) {
        if (voice) {
            this.voice.add(client);
        } else {
            this.voice.remove(client);
        }
    }

    @Override
    public boolean hasClient(Client client) {
        return clients.contains(client);
    }

    @Override
    public int getUsersCount() {
        return clients.size();
    }

    @Override
    public ChannelMode getModes() {
        return modes;
    }
}
