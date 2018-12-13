package com.rbruno.irc.channel;

import com.rbruno.irc.client.Client;

public interface Channel {

    public String getName();

    public Client[] getClients();

    public String getTopic();

    public boolean hasClient(String nickname);

    public String getPassword();

    public boolean isMode(ChannelMode mode);

    public Channel addClient(String nickname);

    public Channel removeClient(String nickname);

    public Channel setTopic(String topic);

    public Channel setPassword(String password);

    public Channel setMode(int code);

}
