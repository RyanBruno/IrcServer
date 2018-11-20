package com.rbruno.irc.command.registration;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.client.LocalClient;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Error;

public class User extends Command {

    public User() {
        super("USER", 4);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        if (!request.getConnection().getNickname().isPresent()) {
            request.getConnection().send(Error.ERR_NEEDMOREPARAMS, "???", ":Nickname required!");
            return;
        }
        
        LocalClient newClient = new LocalClient(request.getConnection(), request.getConnection().getNickname().get(), request.getArgs()[0], request.getArgs()[1], request.getArgs()[2], request.getArgs()[3]);

        request.getConnection().send(1, request.getConnection().getNickname().get(), ":Welcome to the " + Server.getServer().getConfig().getHostname() + " Internet Relay Chat Network " + request.getConnection().getNickname().get());
        //request.getConnection().send(Reply.RPL_LUSERCLIENT, newClient, ":There are " + Server.getServer().getClientManager().getClientCount() + " users and " + Server.getServer().getClientManager().getInvisibleClientCount() + " invisible on 1 servers");
        //request.getConnection().send(Reply.RPL_LUSEROP, newClient, Server.getServer().getClientManager().getOps() + " :operator(s) online");
        //request.getConnection().send(Reply.RPL_LUSERCHANNELS, newClient, Server.getServer().getChannelManger().getNonSecretChannels() + " :channels formed");
        //request.getConnection().send(Reply.RPL_LUSERME, newClient, ":I have " + Server.getServer().getClientManager().getClientCount() + " clients and 1 servers");

        request.getConnection().setClient(newClient);
        request.getConnection().setInvoker(Server.getServer().getClientCommandInvoker());
    }

}
