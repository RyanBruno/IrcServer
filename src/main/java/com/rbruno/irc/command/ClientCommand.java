package com.rbruno.irc.command;

import com.rbruno.irc.client.Client;
import com.rbruno.irc.net.Request;

public abstract class ClientCommand {
    
    private CommandModule commandModule;

    public ClientCommand(CommandModule commandModule) {
        this.commandModule = commandModule;
    }

    public abstract void execute(Request request, Client client);

    public CommandModule getCommandModule() {
        return commandModule;
    }
}
