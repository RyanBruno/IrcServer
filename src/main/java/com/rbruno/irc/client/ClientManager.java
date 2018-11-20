package com.rbruno.irc.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.channel.Channel;

/**
 * Manages all clients local and remote.
 */
public class ClientManager {

    private ArrayList<Client> clients = new ArrayList<Client>();

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client, Optional<String> message) {
        Iterator<Channel> channels = Server.getServer().getChannelManger().getChannels().iterator();
        while (channels.hasNext()) {
            Channel channel = channels.next();
            if (channel.hasClient(client)) {
                channel.quitClient(client, message);
            }
        }
        clients.remove(client);
    }

    public Iterator<Client> iterator() {
        return clients.iterator();
    }

    public Client getClient(String nickname) {
        Iterator<Client> iterator = clients.iterator();

        while (iterator.hasNext()) {
            Client current = iterator.next();
            if (current.getNickname().equals(nickname))
                return current;

        }
        return null;
    }
}
