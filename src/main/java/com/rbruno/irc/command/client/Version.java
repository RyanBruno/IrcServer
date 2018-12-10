package com.rbruno.irc.command.client;

import java.util.Optional;

import com.rbruno.irc.Server;
import com.rbruno.irc.client.Client;
import com.rbruno.irc.command.Command;
import com.rbruno.irc.command.CommandModule;
import com.rbruno.irc.net.Request;
import com.rbruno.irc.reply.Reply;

public class Version extends ClientCommand {

    public Version(CommandModule commandModule) {
        super(commandModule);
    }

    @Override
    public void execute(Request request, Client client) {
        request.getConnection().send(Reply.RPL_VERSION, client.get(), Server.VERSION + " " + Server.getServer().getConfig().getHostname());
    }
}
