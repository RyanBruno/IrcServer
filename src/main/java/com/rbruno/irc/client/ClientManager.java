package com.rbruno.irc.client;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manages all clients local and remote.
 */
public class ClientManager {

    private ArrayList<LocalClient> clients = new ArrayList<LocalClient>();

    public void addClient(LocalClient client) {
        clients.add(client);
    }

    public void removeClient(LocalClient client) {
        clients.remove(client);
    }

    public Iterator<LocalClient> iterator() {
        return clients.iterator();
    }

    public LocalClient getClient(String nickname) {
        Iterator<LocalClient> iterator = clients.iterator();

        while (iterator.hasNext()) {
            LocalClient current = iterator.next();
            if (current.getNickname().equals(nickname))
                return current;

        }
        return null;
    }
}
