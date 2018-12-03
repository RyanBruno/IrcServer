package com.rbruno.irc.command.client;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.ClientCommand;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Admin implements ClientCommand {

    @Override
    public void execute(Request request, Client client) {
        request.getConnection().send(Reply.RPL_ADMINME, client.get(), Server.getServer().getConfig().getHostname() + " :Administrative info");
        if (Server.getServer().getConfig().getAdminLoc1().isPresent()) {
            request.getConnection().send(Reply.RPL_ADMINLOC1, client.get(), Server.getServer().getConfig().getHostname() + " :" + Server.getServer().getConfig().getAdminLoc1().get());
            if (Server.getServer().getConfig().getAdminLoc2().isPresent()) {
                request.getConnection().send(Reply.RPL_ADMINLOC2, client.get(), Server.getServer().getConfig().getHostname() + " :" + Server.getServer().getConfig().getAdminLoc2().get());
                if (Server.getServer().getConfig().getAdminMail().isPresent()) {
                    request.getConnection().send(Reply.RPL_ADMINMAIL, client.get(), Server.getServer().getConfig().getHostname() + " :" + Server.getServer().getConfig().getAdminMail().get());
                }
            }
        }        
    }
}
