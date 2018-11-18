package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Admin extends Command {

    public Admin() {
        super("ADMIN", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
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
