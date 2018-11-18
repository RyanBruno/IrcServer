package com.rbruno.irc.channel;

import java.util.Iterator;
import java.util.Optional;

import com.rbruno.irc.client.Client;

public interface Channel {

    public String getName();

    public String getTopic();

    public void setTopic(Optional<String> topic);
    
    public boolean hasClient(Client client);

    public void addClient(Client client);

    public void removeClient(Client client);

    public Iterator<Client> getIterator();

    public boolean isChanOp(Client client);

    public void setChanOp(Client client, boolean op);

    public boolean isPrivateChannel();

    public boolean isSecrete();

    public boolean isInviteOnly();

    public boolean isOpMustSetTopic();

    public boolean isNoOutsideMessages();

    public boolean isModerated();

    public int getuserLimit();

    public boolean isBanned(Client client);

    public void setBanned(Client client, boolean banned);

    public boolean hasVoice(Client client);

    public void setVoice(Client client, boolean voice);

    public Optional<String> getPassword();

    public void setPassword(Optional<String> password);

    void sendToAll(String message);

    public int getUsersCount();

}
