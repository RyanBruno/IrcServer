package com.rbruno.irc.command;

import com.rbruno.irc.net.Request;

public abstract class RegistrationCommand {

    private CommandModule commandModule;

    public RegistrationCommand(CommandModule commandModule) {
        this.commandModule = commandModule;
    }

    public abstract void execute(Request request);

    public CommandModule getCommandModule() {
        return commandModule;
    }
}
