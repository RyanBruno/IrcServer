package com.rbruno.irc.command.registration;

import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.events.ClientChangedEvent;
import com.rbruno.irc.events.ClientChangedEvent.ClientChangeType;
import com.rbruno.irc.events.Event;
import com.rbruno.irc.events.Listener;
import com.rbruno.irc.net.Request;

public class User extends RegistrationCommand implements Listener {

    public User(RegCommandModule commandModule) {
        super("USER", commandModule);
    }

    @Override
    public Event execute(Request request) {
        if (getCommandModule().getNickname(request.getSocketChannel()) == null) {
            //request.getConnection().send(Error.ERR_NEEDMOREPARAMS, "???", ":Nickname required!");
            // TODO Send
            return null;
        }
        LocalClient newClient = new LocalClient(request.getSocketChannel(), getCommandModule().getNickname(request.getSocketChannel()), request.getArgs()[0], request.getArgs()[1], request.getArgs()[2], request.getArgs()[3]);

        //request.getConnection().send(1, request.getConnection().getNickname().get(), ":Welcome to the " + Server.getServer().getConfig().getHostname() + " Internet Relay Chat Network " + request.getConnection().getNickname().get());
        //request.getConnection().send(Reply.RPL_LUSERCLIENT, newClient, ":There are " + Server.getServer().getClientManager().getClientCount() + " users and " + Server.getServer().getClientManager().getInvisibleClientCount() + " invisible on 1 servers");
        //request.getConnection().send(Reply.RPL_LUSEROP, newClient, Server.getServer().getClientManager().getOps() + " :operator(s) online");
        //request.getConnection().send(Reply.RPL_LUSERCHANNELS, newClient, Server.getServer().getChannelManger().getNonSecretChannels() + " :channels formed");
        //request.getConnection().send(Reply.RPL_LUSERME, newClient, ":I have " + Server.getServer().getClientManager().getClientCount() + " clients and 1 servers");

        return new ClientChangedEvent(newClient, ClientChangeType.CLIENT_REGISTERED);
    }

}
