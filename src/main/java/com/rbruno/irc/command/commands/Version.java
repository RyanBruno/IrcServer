package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Version extends Command {

    public Version() {
        super("VERSION", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        request.getConnection().send(Reply.RPL_VERSION, client.get(), Server.VERSION + " " + Server.getServer().getConfig().getHostname());
    }
}
