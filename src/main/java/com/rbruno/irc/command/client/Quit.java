package com.rbruno.irc.command.client;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.ClientCommand;
import com.rbruno.irc.net.Request;

public class Quit implements ClientCommand {

    @Override
    public void execute(Request request, Optional<Client> client) {
        String message = null;
        if (request.getArgs().length != 0) {
            message = request.getArgs()[0];
        }
        // TODO Emit client quit

    }

}
