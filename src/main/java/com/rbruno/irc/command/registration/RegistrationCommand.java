package com.rbruno.irc.command.registration;

import com.rbruno.irc.net.Request;

public abstract class RegistrationCommand {

    private RegCommandModule commandModule;

    public RegistrationCommand(RegCommandModule commandModule) {
        this.commandModule = commandModule;
    }

    public abstract void execute(Request request);

    public RegCommandModule getCommandModule() {
        return commandModule;
    }
}
