package com.rbruno.irc.channel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.reply.Reply;

public class LocalChannel implements Channel {

    private String name;
    protected Optional<String> topic;

    protected ArrayList<Client> clients;
    protected ArrayList<Client> ops;
    protected ArrayList<Client> banned;
    protected ArrayList<Client> voice;

    private boolean privateChannel;
    private boolean secrete;
    private boolean inviteOnly;
    private boolean opMustSetTopic;
    private boolean noOutSideMessages;
    private boolean moderated;
    private int userLimit;
    private Optional<String> password;

    public LocalChannel(String name) {
        this.name = name;

        clients = new ArrayList<Client>(5);
        ops = new ArrayList<Client>(2);
        banned = new ArrayList<Client>(2);
        voice = new ArrayList<Client>(5);

        userLimit = 10;
        password = Optional.empty();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTopic() {
        if (topic.isPresent())
            return topic.get();
        return "Default Topic";
    }

    public void setTopic(Optional<String> topic) {
        // TODO Send to all
        this.topic = topic;
    }

    @Override
    public void addClient(Client client) {
        // TODO JOIN MSG

        this.sendToAll(":" + client.getNickname() + "!" + client.getUsername() + "@" + client.getHostname() + " JOIN " + this.getName());

        String message = "@ " + this.getName() + " :";
        for (Client current : clients) {
            if (isChanOp(client)) {
                message = message + "@" + current.getNickname() + " ";
            } else if (hasVoice(current)) {
                message = message + "+" + current.getNickname() + " ";
            } else {
                message = message + current.getNickname() + " ";
            }
        }
        client.getConnection().send(Reply.RPL_NAMREPLY, client, message);
        client.getConnection().send(Reply.RPL_ENDOFNAMES, client, this.getName() + " :End of /NAMES list.");
        clients.add(client);
    }
    
    @Override
    public void sendToAll(String message) {
        clients.stream().forEach(new Consumer<Client>() {

            @Override
            public void accept(Client client) {
                    client.getConnection().send(message);
                
            }
        });
    }

    @Override
    public void removeClient(Client client) {
        // TODO QUIT MSG
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
    public boolean isPrivateChannel() {
        return privateChannel;
    }

    @Override
    public boolean isSecrete() {
        return secrete;
    }

    @Override
    public boolean isInviteOnly() {
        return inviteOnly;
    }

    @Override
    public boolean isOpMustSetTopic() {
        return opMustSetTopic;
    }

    @Override
    public boolean isNoOutsideMessages() {
        return noOutSideMessages;
    }

    @Override
    public boolean isModerated() {
        return moderated;
    }

    @Override
    public int getuserLimit() {
        return userLimit;
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
    public Optional<String> getPassword() {
        return password;
    }

    @Override
    public void setPassword(Optional<String> password) {
        this.password = password;
    }

    @Override
    public boolean hasClient(Client client) {
        return clients.contains(client);
    }

    @Override
    public int getUsersCount() {
        return clients.size();
    }

}
