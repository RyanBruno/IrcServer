package com.rbruno.irc.channel;

import java.util.Iterator;

import com.rbruno.irc.client.Client;

public interface Channel {

    public String getName();

    public String getTopic();

    public void setTopic(String topic);

    public boolean hasClient(Client client);

    public void addClient(Client client);

    public void removeClient(Client client);

    public Iterator<Client> getIterator();

    public ChannelMode getModes();

    public boolean isChanOp(Client client);

    public void setChanOp(Client client, boolean op);

    public boolean isBanned(Client client);

    public void setBanned(Client client, boolean banned);

    public boolean hasVoice(Client client);

    public void setVoice(Client client, boolean voice);

    public int getUsersCount();

    public void invitePlayer(Client client);

	public boolean isInvited(Client client);

}
