package com.rbruno.irc.command.registration;

import java.util.function.Function;

import com.rbruno.irc.net.Request;
import com.rbruno.irc.net.Response;

public abstract class RegistrationCommand implements Function<Request, Response> {

    private RegCommandModule commandModule;

    public RegistrationCommand(RegCommandModule commandModule) {
        this.commandModule = commandModule;
    }

    public RegCommandModule getCommandModule() {
        return commandModule;
    }
}
