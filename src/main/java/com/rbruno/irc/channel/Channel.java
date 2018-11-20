package com.rbruno.irc.channel;

import java.util.Iterator;
import java.util.Optional;

import com.rbruno.irc.client.Client;

public interface Channel {

    public String getName();

    public String getTopic();

    public void setTopic(String topic);

    public boolean hasClient(Client client);

    public void addClient(Client client);

    public void partClient(Client client, Optional<String> message);

    public void quitClient(Client client, Optional<String> message);

    public void kickClient(Client client, Optional<String> message);

    public Iterator<Client> getIterator();

    public ChannelMode getModes();

    public boolean isChanOp(Client client);

    public void setChanOp(Client client, boolean op);

    public boolean isBanned(Client client);

    public void setBanned(Client client, boolean banned);

    public boolean hasVoice(Client client);

    public void setVoice(Client client, boolean voice);

    public void sendToAll(String message);

    public int getUsersCount();

}
