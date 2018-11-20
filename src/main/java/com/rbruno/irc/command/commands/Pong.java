package com.rbruno.irc.command.commands;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.net.Request;

public class Pong extends Command {

    public Pong() {
        super("PONG", 0);
    }

    @Override
    public void execute(Request request, Optional<Client> client) {
        super.execute(request, client);
    }
}
