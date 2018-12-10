package com.rbruno.irc.command.client;

import java.util.Optional;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.CommandModule;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Away extends ClientCommand {

    public Away(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request, Client client) {
        if (request.getArgs().length > 0) {
            //TODO Events
            client.setAwayMessage(Optional.ofNullable(request.getArgs()[0]));
            request.getConnection().send(Reply.RPL_NOWAWAY, client, ":You have been marked as being away");
        } else {
            client.setAwayMessage(Optional.empty());
            request.getConnection().send(Reply.RPL_UNAWAY, client, ":You are no longer marked as being away");
        }
    }
}
