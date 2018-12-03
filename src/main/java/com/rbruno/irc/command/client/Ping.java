package com.rbruno.irc.command.client;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;

public class Ping extends Command {

    public Ping() {
        super("PING", 1);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
        request.getConnection().send(":" + Server.getServer().getConfig().getHostname() + " PONG " + client.get());
    }

}
