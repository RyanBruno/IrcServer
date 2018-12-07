package com.rbruno.irc.channel;

import com.rbruno.irc.client.Client;

public interface Channel {

    public String getName();

    public String getTopic();

    public Channel addClient(Client client);

    public Channel removeClient(Client client);

    public Channel setTopic(String topic);

    public boolean hasClient(Client client);

}
