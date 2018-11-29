package com.rbruno.irc.channel;

import java.util.Iterator;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.reply.Reply;

public class IrcMessenger {

    public void sendNames(Channel channel, Client client, Iterator<Client> clients) {
        String message = "@ " + channel.getName() + " :";
        while (clients.hasNext()) {
            Client current = clients.next();
            if (channel.isChanOp(client)) {
                message = message + "@" + current.getNickname() + " ";
            } else if (channel.hasVoice(current)) {
                message = message + "+" + current.getNickname() + " ";
            } else {
                message = message + current.getNickname() + " ";
            }
        }
        client.getConnection().send(Reply.RPL_NAMREPLY, client, message);
        client.getConnection().send(Reply.RPL_ENDOFNAMES, client, channel.getName() + " :End of /NAMES list.");
    }

    public void sendJoinMessage(Channel channel, Client client) {
        channel.sendToAll(":" + client.getAbsoluteName() + " JOIN " + channel.getName());
    }

    public void sendTopic(Channel channel, Client client) {
        client.getConnection().send(Reply.RPL_TOPIC, client, channel.getName() + " :" + channel.getTopic());
    }

    public void sendTopicToAll(Channel channel, Iterator<Client> clients) {
        while (clients.hasNext()) {
            Client client = clients.next();
            sendTopic(channel, client);
        }
    }

    public void clientQuit(Channel channel, Client client, String message) {
        channel.sendToAll(":" + client.getAbsoluteName() + " QUIT :" + message);
    }

    public void sendQuitMessage(Iterator<Channel> allChannels, Client client, String message) {
        while (allChannels.hasNext()) {
            Channel channel = allChannels.next();

            if (!channel.hasClient(client)) {
                continue;
            }
            
            clientQuit(channel, client, message);
        }
    }

    public void clientKick(Channel channel, Client client, String message) {
        channel.sendToAll(":" + client.getAbsoluteName() + " KICK " + channel.getName() + " " + client.getNickname() + ":" + message);        
    }

    public void clientPart(Channel channel, Client client, String message) {
        channel.sendToAll(":" + client.getAbsoluteName() + " QUIT :" + message);        
    }

    public void invitePlayer(Channel channel, Client invitor, Client target) {
        invitor.getConnection().send(Reply.RPL_INVITING, invitor, target.getNickname() + " " + channel.getName());
        target.getConnection().send(":" + invitor.getAbsoluteName() + " INVITE " + target.getNickname() + " " + channel.getName());       
    }

}
